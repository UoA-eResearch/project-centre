import re
import sys
import db
import pprint
from base_db import DB

id_old_new_maps = {
    'kpi': {},
}

affil_map = {}
db_old = DB(**db.config_old)
db_new = DB(**db.config_new)
pp = pprint.PrettyPrinter(indent=4)

def get_new_division_id(institution, division, department):
  key = '%s,%s,%s' % (institution, division, department)
  divisionId = affil_map[key]
  return divisionId

db_new.query("INSERT INTO projectactiontype (name) VALUES ('Review')")
db_new.query("INSERT INTO projectactiontype (name) VALUES ('FollowUp')")
db_new.query("INSERT INTO projectactiontype (name) VALUES ('AdviserAction')")
db_new.query("INSERT INTO projectactiontype (name) VALUES ('Survey')")

db_new.query("INSERT INTO serviceinstancestatus (name) VALUES ('Active')")
db_new.query("INSERT INTO serviceinstancestatus (name) VALUES ('Pending')")
db_new.query("INSERT INTO serviceinstancestatus (name) VALUES ('Closed')")

# institution --> division
tmp = db_old.query('SELECT * FROM institution')
for t in tmp:
  # if t['code'] in [ 'uoa', 'nesi']:
    key1 = '%s,,' % t['id']
    key2 = '%s,,' % t['name']
    db_new.query("INSERT INTO division (name, code) VALUES ('%s', '%s')" % (t['name'], t['code']))
    lastId = db_new.getLastInsertId()
    affil_map[key1] = lastId
    affil_map[key2] = lastId

# division --> division 
tmp = db_old.query('SELECT d.id, d.institutionId, d.name, d.code, i.name AS institutionName FROM division d INNER JOIN institution i ON d.institutionId = i.id')
for t in tmp:
  parentKey = '%s,,' % t['institutionId']
  key1 = '%s,%s,' % (t['institutionId'], t['id'])
  key2 = '%s,%s,' % (t['institutionName'], t['name'])
  if parentKey in affil_map:
    parentId = affil_map[parentKey]
    db_new.query("INSERT INTO division (name, code, parent_id) VALUES ('%s', '%s', %s)" % (
      t['name'], t['code'], parentId))
    lastId = db_new.getLastInsertId()
    affil_map[key1] = lastId
    affil_map[key2] = lastId
    db_new.query("INSERT INTO division_children (division_id, child_id) VALUES (%s, %s)" % (parentId, lastId))

# department --> division 
query = '''SELECT dep.id, dep.institutionId, dep.divisionId, dep.name, dep.code,
  i.name AS institutionName, d.name as divisionName 
  FROM department dep
  INNER JOIN division d ON d.id = dep.divisionId 
  INNER JOIN institution i ON dep.institutionId = i.id'''
tmp = db_old.query(query)
for t in tmp:
  grandParentKey = '%s,,' % (t['institutionId'])
  parentKey = '%s,%s,' % (t['institutionId'], t['divisionId'])
  key1 = '%s,%s,%s' % (t['institutionId'], t['divisionId'], t['id'])
  key2 = '%s,%s,%s' % (t['institutionName'], t['divisionName'], t['name'])
  if parentKey in affil_map:
    parentId = affil_map[parentKey]
    grandParentId = affil_map[grandParentKey]
    db_new.query("INSERT INTO division (name, code, parent_id) VALUES ('%s', '%s', %s)" % (
      t['name'], t['code'], parentId))
    lastId = db_new.getLastInsertId()
    affil_map[key1] = lastId
    affil_map[key2] = lastId
    db_new.query("INSERT INTO division_children (division_id, child_id) VALUES (%s, %s)" % (parentId, lastId))
    db_new.query("INSERT INTO division_children (division_id, child_id) VALUES (%s, %s)" % (grandParentId, lastId))

# kpi --> kpi
tmp = db_old.query('SELECT * FROM kpi')
for t in tmp:
  db_new.query("INSERT INTO kpi (number, type, title, measures) VALUES (%s, '%s', '%s', '%s')" % (
    t['id'], t['type'], t['title'], t['measures']))
  id_old_new_maps['kpi'][t['id']] = db_new.getLastInsertId()

# kpicode --> kpicategory
tmp = db_old.query('SELECT * FROM kpicode')
for t in tmp:
  db_new.query("INSERT INTO kpicategory (kpi_id, name) VALUES (%s, '%s')" % (id_old_new_maps['kpi']['9'], t['code']))

# researcherrole --> personrole
tmp = db_old.query('SELECT * FROM researcherrole')
for t in tmp:
  db_new.query("INSERT INTO personrole (name) VALUES ('%s')" % (t['name']))

# adviserrole --> personrole
tmp = db_old.query('SELECT * FROM adviserrole')
for t in tmp:
  if t['name'] not in ['NIWA Contact', 'BlueFern Contact']:
    db_new.query("INSERT INTO personrole (name) VALUES ('%s')" % (t['name']))

# facility --> facility
tmp = db_old.query('SELECT * FROM facility')
for t in tmp:
  db_new.query("INSERT INTO facility (name) VALUES ('%s')" % (t['name']))

# institutionalrole --> divisional_role 
tmp = db_old.query('SELECT * FROM institutionalrole')
for t in tmp:
  db_new.query("INSERT INTO divisional_role (name) VALUES ('%s')" % (t['name']))

# project_status --> projectstatus 
tmp = db_old.query('SELECT * FROM project_status')
for t in tmp:
  db_new.query("INSERT INTO projectstatus (name) VALUES ('%s')" % (t['name']))

# projecttype --> projecttype
tmp = db_old.query('SELECT * FROM projecttype WHERE name = \'Institution\' OR name = \'Commercial\'')
for t in tmp:
  db_new.query("INSERT INTO projecttype (name) VALUES ('%s')" % (t['name']))

# researchoutputtype --> researchoutputtype
tmp = db_old.query('SELECT * FROM researchoutputtype')
for t in tmp:
  db_new.query("INSERT INTO researchoutputtype (name) VALUES ('%s')" % (t['name']))

# researcher_status --> personstatus 
tmp = db_old.query('SELECT * FROM researcher_status')
for t in tmp:
  db_new.query("INSERT INTO personstatus (name) VALUES ('%s')" % t['name'])

db_old.close()
db_new.close()

