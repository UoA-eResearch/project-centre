import _mysql

class DB(object):

  def __init__(self, host, db, user, password):
    self._host = host
    self._db = db
    self._user = user
    self._password = password
    # Open a connection to the database 
    try:
      self._conn = _mysql.connect(host=self._host, db=self._db, user=self._user, passwd=self._password)
      if self._conn:
        self._conn.set_character_set('utf8')
      else:
        raise Exception("No connection has been made.")
    except Exception, e:
      print "Could not open the connection",e


  def query(self, sql_query):
    ''' Run a SQL query against the database '''
    result = None
    try:
      self._conn.query(sql_query)
      r = self._conn.store_result()
      if r:
        result = r.fetch_row(maxrows=0,how=1)
      return result
    except:
      raise 

  def getLastInsertId(self):
    ''' Returns ID of last insert '''
    return self.query('SELECT last_insert_id() AS lii')[0]['lii']

  def close(self):
    ''' Close the connection to the database '''
    if self._conn:
      self._conn.close()

class NoResultError(Exception):
  """If no result is returned from a query, but a result is expected."""
  def __init__(self, message):
    """Initialisation."""
    Exception.__init__(self, message)

class TooManyResultsError(Exception):
  """If too many results are returned from a query (e.g., when a result should be unique)."""
  def __init__(self, message):
    """Initialisation."""
    Exception.__init__(self, message)

