import pymysql, pymysql.cursors
assert pymysql.paramstyle == "pyformat"  # ie: %
assert pymysql.apilevel == "2.0"

class DB(object):

  def __init__(self, host, port, db, user, password):
    db_opts = {
      'host': host,
      'port': port,
      'db': db,
      'user': user,
      'password': password
    }
    if 'password' in db_opts:
      db_opts['passwd'] = db_opts.pop('password')
    if 'port' in db_opts:
      db_opts['port'] = int(db_opts['port'])
    db_opts['charset'] = 'utf8'
    db_opts['use_unicode'] = True
    db_opts['cursorclass'] = pymysql.cursors.DictCursor
    self._db = pymysql.connect(**db_opts)

  def query(self, *args):
    ''' Run a SQL query against the database '''
    cursor = self._db.cursor()
    cursor.execute(*args)
    result = cursor.fetchall()
    self._db.commit()
    return result

  def getLastInsertId(self):
    ''' Returns ID of last insert '''
    return self.query('SELECT last_insert_id() AS lii')[0]['lii']

  def close(self):
    ''' Close the connection to the database '''
    pass

