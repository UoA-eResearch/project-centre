###############################################################################
# Copy a project from old database to new database
###############################################################################

import re
import sys
import db
import pprint
import argparse
from base_db import DB

def get_value_for_id(db, table, field, id):
  query = "SELECT %s FROM %s WHERE id = %s" % (field, table, id)
  try:
    result = db.query(query)
  except:
    print 'Error running query "%s"' % query
    raise
  if result:
    if len(result) > 1:
      raise Exception('More than one result for query "%s"' % query)
    elif len(result) == 0:
      return None
    else:
      return result[0][field]
  else:
    return None

def get_id_for_value(db, table, field, value, isNumber=False):
  if isNumber:
    query = "SELECT id FROM %s WHERE %s = %s" % (table, field, value)
  else:
    query = "SELECT id FROM %s WHERE %s = '%s'" % (table, field, value)
  try:
    result = db.query(query)
  except:
    print 'Error running query "%s"' % query
    raise
  if result:
    if len(result) > 1:
      raise Exception('More than one result for query "%s"' % query)
    elif len(result) == 0:
      return None
    else:
      return result[0]['id']
  else:
    return None

def get_division_id_for_affiliation(institution, division, department):
  if department:
    query = "SELECT code FROM department WHERE name = '%s'" % (department)
  elif division:
    query = "SELECT code FROM division WHERE name = '%s'" % (division)
  elif institution:
    query = "SELECT code FROM institution WHERE name = '%s'" % (institution)
  else:
    raise Exception('No department, division or institution specified')
  result = db_old.query(query)
  if not result:
    raise Exception('Affiliation not found: [%s,%s,%s]' % (institution, division, department))
  code = result[0]['code']
  query = "SELECT id FROM division WHERE code = '%s'" % (code)
  result = db_new.query(query)
  if not result:
    raise Exception('Division not found for code \'%s\'' % code)
  elif len(result) > 1:
    raise Exception('More than one division found for code \'%s\'' % code)
  return result[0]['id']

def migrate_researcher(researcher):
  print 'Migrating researcher %s' % researcher['fullName']
  statusName = get_value_for_id(db_old, 'researcher_status', 'name', researcher['statusId'])
  newStatusId = get_id_for_value(db_new, 'personstatus', 'name', statusName, False)

  if researcher['notes']:
    tmp = researcher['notes'].replace("\'", "\\'")
    researcher['notes'] = tmp
  if "'" in researcher['fullName']:
    tmp = researcher['fullName'].replace("\'", "\\'")
    researcher['fullName'] = tmp
  query = '''INSERT INTO person (full_name,preferred_name,email,phone,picture_url,start_date,end_date,notes,status_id) ''' \
          '''VALUES ('%s','%s','%s','%s','%s','%s','%s','%s',%s)''' % (researcher['fullName'],
            researcher['preferredName'], researcher['email'], researcher['phone'],
            researcher['pictureUrl'], researcher['startDate'], researcher['endDate'], researcher['notes'], newStatusId )
  db_new.query(query)
  personId = db_new.getLastInsertId()

  # migrate researcher properties: researcher_properties --> person_properties
  print 'Migrating researcher properties'
  props = db_old.query('SELECT * FROM researcher_properties WHERE researcherId = %s' % researcher['id'])
  if props:
    for prop in props:
      if prop['propname'] not in [ 'drupalId', 'noWysiwyg' ]:
        if "'" in prop['propvalue']:
          tmpval = prop['propvalue'].replace("\'", "\\'")
          prop['propvalue'] = tmpval
        db_new.query("INSERT INTO person_properties (person_id, propname, propvalue, timestamp) VALUES (%s, '%s', '%s', '%s')" % (
          personId, prop['propname'], prop['propvalue'], prop['timestamp']))
        # create identity
        if (prop['propname'] == 'eppn'):
          query = "INSERT INTO identity (person_id, username, expires, token) VALUES (%s, '%s', '%s', '%s')" % (
            personId, prop['propvalue'].split('@')[0], '2018-12-31 11:00:00', '$2a$08$IpjtX18sK6qPZxb/qlMAwO69oed/nuzjKyCDVj0/lIARByca4l.ie')
          db_new.query(query)
          query = "INSERT INTO identity (person_id, username, expires) VALUES (%s, '%s', '%s')" % (
            personId, prop['propvalue'], '2018-12-31 11:00:00')
          db_new.query(query)

  divisionId = get_division_id_for_affiliation(researcher['institution'], researcher['division'], researcher['department'])
  institutionalRoleName = get_value_for_id(db_old, 'institutionalrole', 'name', researcher['institutionalRoleId'])
  newDivisionalRoleId = get_id_for_value(db_new, 'divisional_role', 'name', institutionalRoleName, False)

  # add to person_affiliation table
  print 'Create researcher affiliation'
  query='''INSERT INTO person_affiliation (person_id, division_id, divisional_role_id) VALUES (%s,%s,%s)''' % (personId, divisionId, newDivisionalRoleId)
  try:
    db_new.query(query)
  except:
    print query
    print 'Failed to create researcher affiliation. Skipping researcher'

  return personId

def migrate_adviser(adviser):
  print 'Migrating adviser %s' % adviser['fullName']
  statusId = status_active
  if adviser['endDate']:
    statusId = status_closed
  if researcher['notes']:
    tmp = adviser['notes'].replace("\'", "\\'")
    adviser['notes'] = tmp
  if "'" in adviser['fullName']:
    tmp = adviser['fullName'].replace("\'", "\\'")
    adviser['fullName'] = tmp
  if adviser['endDate']: 
    query = '''INSERT INTO person (full_name,email,phone,picture_url,start_date,end_date,notes,status_id) ''' \
            '''VALUES ('%s','%s','%s','%s','%s','%s','%s',%s)''' % (adviser['fullName'],
            adviser['email'], adviser['phone'], adviser['pictureUrl'], adviser['startDate'], adviser['endDate'], adviser['notes'], statusId)
  else:
    query = '''INSERT INTO person (full_name,email,phone,picture_url,start_date,end_date,notes,status_id) ''' \
            '''VALUES ('%s','%s','%s','%s','%s',%s,'%s',%s)''' % (adviser['fullName'],
            adviser['email'], adviser['phone'], adviser['pictureUrl'], adviser['startDate'], 'NULL', adviser['notes'], statusId)

  db_new.query(query)
  personId = db_new.getLastInsertId()

  # migrate adviser properties: adviser_properties --> person_properties
  print 'Migrating adviser properties'
  props = db_old.query('SELECT * FROM adviser_properties WHERE adviserId = %s' % adviser['id'])
  if props:
    for prop in props:
      if prop['propname'] not in [ 'drupalId', 'noWysiwyg' ]:
        if "'" in prop['propvalue']:
          tmpval = prop['propvalue'].replace("\'", "\\'")
          prop['propvalue'] = tmpval
        db_new.query("INSERT INTO person_properties (person_id, propname, propvalue, timestamp) VALUES (%s, '%s', '%s', '%s')" % (
          personId, prop['propname'], prop['propvalue'], prop['timestamp']))
        # create identity
        if (prop['propname'] == 'eppn'):
          query = "INSERT INTO identity (person_id, username, expires, token) VALUES (%s, '%s', '%s', '%s')" % (
            personId, prop['propvalue'].split('@')[0], '2018-12-31 11:00:00',
            '$2a$08$IpjtX18sK6qPZxb/qlMAwO69oed/nuzjKyCDVj0/lIARByca4l.ie')
          db_new.query(query)
          query = "INSERT INTO identity (person_id, username, expires) VALUES (%s, '%s', '%s')" % (
            personId, prop['propvalue'], '2018-12-31 11:00:00')
          db_new.query(query)

  divisionId = get_division_id_for_affiliation(adviser['institution'], adviser['division'], adviser['department'])

  # add to person_affiliation table
  print 'Create adviser affiliation'
  query='''INSERT INTO person_affiliation (person_id, division_id, divisional_role_id) VALUES (%s,%s,%s)''' % (personId, divisionId, 1)
  try:
    db_new.query(query)
  except:
    print query
    print 'Failed to create adviser affiliation. Skipping researcher'

  return personId

def migrate_researcher_by_id(researcherId):
  return migrate_researcher(db_old.query('SELECT * from researcher where id = %s' % researcherId)[0])
   
def migrate_adviser_by_id(adviserId):
  return migrate_adviser(db_old.query('SELECT * from adviser where id = %s' % adviserId)[0])


### main

# handle command-line parameters 
parser = argparse.ArgumentParser(description='Migrate project from old database to new database.')
parser.add_argument('-c', '--projectcode', nargs='+', type=str, help='Project code', required=True)
args = parser.parse_args()

# set up db connections
db_old = DB(**db.config_old)
db_new = DB(**db.config_new)

for code in args.projectcode:

  projects = db_old.query('SELECT * FROM project WHERE projectCode = \'%s\'' % code)
  if not projects:
    print 'No project with code %s found.' % code
    continue
  elif len(projects) > 1:
    print 'More than one project with code %s found.' % code
    continue
  else:
    tmpProject = db_new.query('SELECT * FROM project WHERE code = \'%s\'' % code)
    if tmpProject:
      print 'Project with code %s already exists in new database.' % code
      continue

  ### project --> project. if project already exists, exit
  print 'Migrating project %s' % code
  t = projects[0]
  oldProjectId = t['id']
  divisionId = get_division_id_for_affiliation(t['hostInstitution'], t['division'], t['department'])
  statusName = get_value_for_id(db_old, 'project_status', 'name', t['statusId'])
  newStatusId = get_id_for_value(db_new, 'projectstatus', 'name', statusName, False)
  if not t['endDate']:
    t['endDate'] = '2100-01-01'
  if not t['nextReviewDate']:
    t['nextReviewDate'] = t['endDate']
  query='''INSERT INTO project (code,type_id,title,description,start_date,next_review_date,''' \
      '''end_date,requirements,notes,todo,status_id,last_modified,creation_date) VALUES(''' \
      ''''%s',%s,'%s','%s','%s','%s','%s','%s','%s','%s',%s,'%s','%s')''' % (t['projectCode'], t['projectTypeId'],
      t['name'].replace("\'", "\\'"), t['description'].replace("\'", "\\'"),
      t['startDate'], t['nextReviewDate'], t['endDate'], t['requirements'].replace("\'", "\\'"),
      t['notes'].replace("\'", "\\'"), t['todo'].replace("\'", "\\'"), newStatusId,
      t['lastModified'], t['creationDate'])
  try:
    db_new.query(query)
  except:
    print t['projectCode']
    print query
    print 'Failed to migrate project details. Skipping remainder of project %s' % code
    continue
  newProjectId = db_new.getLastInsertId()

  ### create project_division entry
  print 'Linking project to division'
  query='''INSERT INTO project_division (project_id, division_id) VALUES (%s,%s)''' % (newProjectId, divisionId)
  try:
    db_new.query(query)
  except:
    print t['projectCode']
    print query
    print 'Failed to create project_division entry. Skipping remainder of project %s' % code
    continue

  ### project_properties --> project_properties
  print 'Migrating project properties'
  props = db_old.query('SELECT * FROM project_properties WHERE projectId = %s' % oldProjectId)
  if props:
    for prop in props:
      facilityName = get_value_for_id(db_old, 'facility', 'name', prop['facilityId'])
      newFacilityId = get_id_for_value(db_new, 'facility', 'name', facilityName, False)
      db_new.query("INSERT INTO project_properties (project_id, facility_id, propname, propvalue) VALUES (%s, %s, '%s', '%s')" % (
        newProjectId, newFacilityId, prop['propname'], prop['propvalue']))

  ### migrate researchers on project
  researchers = db_old.query('''SELECT * from researcher where id in (SELECT distinct r.id FROM researcher r ''' \
    '''INNER JOIN researcher_project rp ON r.id = rp.researcherId ''' \
    '''INNER JOIN project p ON p.id = rp.projectId WHERE p.id = %s)''' % oldProjectId)
  if researchers and researchers is not None:
    for researcher in researchers:
      # check if person exists in new database
      personId = get_id_for_value(db_new, 'person', 'email', researcher['email'], False)

      if personId is None: # person does not yet exist in new database
        personId = migrate_researcher(researcher)

      # add person to person_project table: researcher_project --> person_project
      rps = db_old.query('SELECT * FROM researcher_project WHERE researcherId = %s AND projectId = %s' % (researcher['id'], oldProjectId))
      for rp in rps:
        if rp['notes'] and "'" in rp['notes']:
          tmp = rp['notes'].replace("\'", "\\'")
          rp['notes'] = tmp
        if rp['notes'] is None:
          rp['notes'] = ''
       
        print 'Linking researcher to project'
        researcherRoleName = get_value_for_id(db_old, 'researcherrole', 'name', rp['researcherRoleId'])
        personRoleId = get_id_for_value(db_new, 'personrole', 'name', researcherRoleName, False)
        db_new.query("INSERT INTO person_project (person_id, project_id, person_role_id, notes) VALUES (%s, %s, %s, '%s')" % (
          personId, newProjectId, personRoleId, rp['notes']))
    
  ### migrate advisers on project
  advisers = db_old.query('''SELECT * from adviser where id in (SELECT distinct a.id FROM adviser a ''' \
    '''INNER JOIN adviser_project ap ON a.id = ap.adviserId ''' \
    '''INNER JOIN project p ON p.id = ap.projectId WHERE p.id = %s)''' % oldProjectId)
  if advisers and advisers is not None:
    status_active = db_new.query('SELECT id FROM personstatus WHERE name = \'Active\'')[0]['id']
    status_closed = db_new.query('SELECT id FROM personstatus WHERE name = \'Closed\'')[0]['id']
    for adviser in advisers:
      # check if person exists in new database
      personId = get_id_for_value(db_new, 'person', 'email', adviser['email'], False)

      if personId is None: # person does not yet exist in new database
        personId = migrate_adviser(adviser)

      # if not already on project as researcher: add person to person_project table: adviser_project --> person_project
      persons_on_project = db_new.query('SELECT * FROM person_project WHERE person_id = %s AND project_id = %s' % (personId, newProjectId))
      if not persons_on_project:
        print 'Linking adviser to project'
        aps = db_old.query('SELECT * FROM adviser_project WHERE adviserId = %s AND projectId = %s' % (adviser['id'], oldProjectId))
        for ap in aps:
          if ap['notes'] and "'" in ap['notes']:
            tmp = ap['notes'].replace("\'", "\\'")
            ap['notes'] = tmp
          if ap['notes'] is None:
            ap['notes'] = ''
       
          adviserRoleName = get_value_for_id(db_old, 'adviserrole', 'name', ap['adviserRoleId'])
          personRoleId = get_id_for_value(db_new, 'personrole', 'name', adviserRoleName, False)
          db_new.query("INSERT INTO person_project (person_id, project_id, person_role_id, notes) VALUES (%s, %s, %s, '%s')" % (
            personId, newProjectId, personRoleId, ap['notes']))

  ### migrate attachment --> project_externalreference
  print 'Migrating project attachments'
  tmp = db_old.query('SELECT * FROM attachment WHERE projectId = %s' % oldProjectId)
  if tmp and tmp is not None:
    for t in tmp:
      db_new.query("INSERT INTO project_externalreference (project_id, description, date, uri) VALUES (%s, '%s', '%s', '%s')" % (
        newProjectId, t['description'], t['date'], t['link']))

  # researchoutput --> project_researchoutput
  tmp = db_old.query('SELECT * FROM researchoutput WHERE projectId = %s' % oldProjectId)
  for t in tmp:
    print 'Migrating research output'
    if t['researcherId']:
      email = get_value_for_id(db_old, 'researcher', 'email', t['researcherId'])
      personId = get_id_for_value(db_new, 'person', 'email', email, False)
      if personId is None:
        personId = migrate_researcher_by_id(t['researcherId'])
    elif t['adviserId']:
      email = get_value_for_id(db_old, 'adviser', 'email', t['adviserId'])
      personId = get_id_for_value(db_new, 'person', 'email', email, False)
      if personId is None:
        personId = migrate_adviser_by_id(t['adviserId'])
    else:
      raise Exception('Neither researcherId nor adviserId specified')

    if personId is None:
      print 'Failed to get person Id from email %s' % email
      sys.exit(1)

    for field in ['link','DOI','date']:
      if not t[field]:
        t[field] = ''
    try:
      if t['description'] and "'" in t['description']:
        tmp = t['description'].replace("\'", "\\'")
        t['description'] = tmp
      query = "INSERT INTO project_researchoutput (project_id,type_id,description,uri,doi,date_reported) VALUES (%s,%s,'%s','%s','%s','%s')" % (
        newProjectId,t['typeId'],t['description'],t['link'],t['DOI'],t['date'])
      db_new.query(query)
    except:
      print query
      pprint.PrettyPrinter(indent=4).pprint(t)
      raise

  # projectreview --> project_action
  tmp = db_old.query('SELECT * FROM projectreview WHERE projectId = %s' % oldProjectId)
  actionTypeId = db_new.query('SELECT id FROM projectactiontype WHERE name = \'Review\'')[0]['id']
  for t in tmp:
    print 'Migrating project review'
    if t['notes'] and "'" in t['notes']:
      tmp = t['notes'].replace("\'", "\\'")
      t['notes'] = tmp
    email = get_value_for_id(db_old, 'adviser', 'email', t['adviserId'])
    personId = get_id_for_value(db_new, 'person', 'email', email, False)
    if personId is None:
      personId = migrate_adviser_by_id(t['adviserId'])

    try:
      query = "INSERT INTO project_action (project_id, person_id, action_type_id, date, notes) VALUES (%s, %s, %s, '%s', '%s')" % (
        newProjectId, personId, actionTypeId, t['date'], t['notes'])
      db_new.query(query)
    except:
      print t
      print query
      raise

  # adviseraction --> project_action
  tmp = db_old.query('SELECT * FROM adviseraction WHERE projectId = %s' % oldProjectId)
  actionTypeId = db_new.query('SELECT id FROM projectactiontype WHERE name = \'AdviserAction\'')[0]['id']
  for t in tmp:
    print 'Migrating adviser action'
    if t['action'] and "'" in t['action']:
      tmp = t['action'].replace("\'", "\\'")
      t['action'] = tmp

    email = get_value_for_id(db_old, 'adviser', 'email', t['adviserId'])
    personId = get_id_for_value(db_new, 'person', 'email', email, False)
    if personId is None:
      personId = migrate_adviser_by_id(t['adviserId'])

    try:
      query = "INSERT INTO project_action (project_id, person_id, action_type_id, date, notes) VALUES (%s, %s, %s, '%s', '%s')" % (
        newProjectId, personId, actionTypeId, t['date'], t['action'])
      db_new.query(query)
    except:
      print t
      print query
      raise

  # projectfollowup --> project_action
  tmp = db_old.query('SELECT * FROM projectfollowup WHERE projectId = %s' % oldProjectId)
  for t in tmp:
    print 'Migrating project follow-up'
    if 'Performance Improvements:' in t['notes'] and 'Future Needs:' in t['notes']:
      actionTypeId = db_new.query('SELECT id FROM projectactiontype WHERE name = \'Survey\'')[0]['id']
    else:
      actionTypeId = db_new.query('SELECT id FROM projectactiontype WHERE name = \'FollowUp\'')[0]['id']

    if t['researcherId']:
      email = get_value_for_id(db_old, 'researcher', 'email', t['researcherId'])
      personId = get_id_for_value(db_new, 'person', 'email', email, False)
      if personId is None:
        personId = migrate_researcher_by_id(t['researcherId'])
    elif t['adviserId']:
      email = get_value_for_id(db_old, 'adviser', 'email', t['adviserId'])
      personId = get_id_for_value(db_new, 'person', 'email', email, False)
      if personId is None:
        personId = migrate_adviser_by_id(t['adviserId'])
    else:
      raise Exception('Neither researcherId nor adviserId specified')

    if t['notes'] and "'" in t['notes']:
      tmp = t['notes'].replace("\'", "\\'")
      t['notes'] = tmp
    try:
      query = "INSERT INTO project_action (project_id, person_id, action_type_id, date, notes) VALUES (%s, %s, %s, '%s', '%s')" % (
        newProjectId, personId, actionTypeId, t['date'], t['notes'])
      db_new.query(query)
    except:
      print t
      print query
      raise

  # project_kpi --> project_kpi
  tmp = db_old.query('SELECT * FROM project_kpi WHERE projectId = %s' % oldProjectId)
  for t in tmp:
    print 'Migrating project kpi'
    if t['code'] and int(t['code']) > 0:
      codeName = get_value_for_id(db_old, 'kpicode', 'code', t['code'])
      kpiCategoryId = get_id_for_value(db_new, 'kpicategory', 'name', codeName, False)
    else:
      kpiCategoryId = 'NULL'

    if not t['value']:
      t['value'] = 'NULL'

    if t['notes'] and "'" in t['notes']:
      tmp = t['notes'].replace("\'", "\\'")
      t['notes'] = tmp

    email = get_value_for_id(db_old, 'adviser', 'email', t['adviserId'])
    personId = get_id_for_value(db_new, 'person', 'email', email, False)
    if personId is None:
      personId = migrate_adviser_by_id(t['adviserId'])

    kpiTitle = get_value_for_id(db_old, 'kpi', 'title', t['kpiId'])
    newKpiId = get_id_for_value(db_new, 'kpi', 'title', kpiTitle, False)

    try:
      query = "INSERT INTO project_kpi (kpi_id,project_id,date,person_id,value,notes,kpi_category_id) VALUES (%s,%s,'%s',%s,%s,'%s',%s)" % (
        newKpiId, newProjectId, t['date'], personId, t['value'], t['notes'], kpiCategoryId)
      db_new.query(query)
    except:
      print t
      print query
      raise

  # project_facility --> project_facility
  tmp = db_old.query('SELECT * FROM project_facility WHERE projectId = %s' % oldProjectId)
  for t in tmp:
    print 'Migrating project facility'
    facilityName = get_value_for_id(db_old, 'facility', 'name', t['facilityId'])
    newFacilityId = get_id_for_value(db_new, 'facility', 'name', facilityName, False)

    try:
      query = "INSERT INTO project_facility (project_id,facility_id) VALUES (%s,%s)" % (newProjectId, newFacilityId)
      db_new.query(query)
    except:
      print t
      print query
      raise

  print ''

db_old.close()
db_new.close()
