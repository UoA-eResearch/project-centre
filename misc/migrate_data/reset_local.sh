#!/bin/bash

#mysql -u root --execute 'drop database eresearch; create database eresearch'
#mysql -u root eresearch < ./eresearch.schema
mysql -u root --execute 'DROP DATABASE project_centre; CREATE DATABASE project_centre'
mysql -u root --execute "GRANT ALL PRIVILEGES ON project_centre.* TO 'uoaeresearch'@'localhost' IDENTIFIED BY 'uoaeresearch'"
mysql -u root project_centre < ../sql/mysql.schema
python migrate_basics.py
python migrate_project.py -c rvmf00001 rvmf00002 rvmf00003 rvmf00004 rvmf00005 rvmf00006 rvmf00007 rvmf00008 rvmf00009 rvmf00010 rvmf00011 rvmf00012 rvmf00013 rvmf00014 rvmf00015 rvmf00016 rvmf00017 rvmf00018 rvmf00019 rvmf00020 rvmf00021 rvmf00022 rvmf00023 rvmf00024 rvmf00029 rvmf00030 rvmf00031 rvmf00032 rvmf00033 rvmf00034 rvmf00035 rvmf00036 rvmf00037 rvmf00038 rvmf00039 rvmf00040 rvmf00041 rvmf00042 rvmf00043 rvmf00044 rvmf00045 rvmf00046 rvmf00047 rvmf00048 rvmf00050 rvmf00051 rvmf00052 rvmf00053 rvmf00054 rvmf00055 uoa00001 uoa00035 uoa00074 uoa00112 uoa00338 uoa00348 uoa00398
mysql -u root project_centre --execute "INSERT INTO authzrole (identity_id, role) VALUES ((SELECT id FROM identity WHERE username = 'mfel395'), 'ROLE_ADMIN')"
mysql -u root project_centre --execute "INSERT INTO authzrole (identity_id, role) VALUES ((SELECT id FROM identity WHERE username = 'nyou045'), 'ROLE_ADMIN')"
mysql -u root project_centre --execute "INSERT INTO authzrole (identity_id, role) VALUES ((SELECT id FROM identity WHERE username = 'rbur004'), 'ROLE_ADMIN')"
mysql -u root project_centre --execute "INSERT INTO authzrole (identity_id, role) VALUES ((SELECT id FROM identity WHERE username = 'rhos012'), 'ROLE_ADMIN')"
mysql -u root project_centre --execute "INSERT INTO authzrole (identity_id, role) VALUES ((SELECT id FROM identity WHERE username = 'mfel395@auckland.ac.nz'), 'ROLE_ADMIN')"
mysql -u root project_centre --execute "INSERT INTO authzrole (identity_id, role) VALUES ((SELECT id FROM identity WHERE username = 'nyou045@auckland.ac.nz'), 'ROLE_ADMIN')"
mysql -u root project_centre --execute "INSERT INTO authzrole (identity_id, role) VALUES ((SELECT id FROM identity WHERE username = 'rbur004@auckland.ac.nz'), 'ROLE_ADMIN')"
mysql -u root project_centre --execute "INSERT INTO authzrole (identity_id, role) VALUES ((SELECT id FROM identity WHERE username = 'rhos012@auckland.ac.nz'), 'ROLE_ADMIN')"
mysql -u root project_centre --execute "INSERT INTO service (drupal_id, name) VALUES (1, 'Research VM Service')"

vm_service_schema='{ "$schema": "http://json-schema.org/draft-04/schema#", "description": "Schema for research VM instances", "type": "object", "properties": { "cpus": { "type": "integer" }, "os": { "type": "string" }, "memory": { "type": "string", "pattern": "^[0-9]+ [MGT]B$" }, "provision_date": { "type": "string", "pattern": "((19|20)[0-9][0-9])-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])" }, "end_date": { "type": "string", "pattern": "((19|20)[0-9][0-9])-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])" }, "names": { "type": "array", "minItems": 1, "items": { "type": "string", "pattern": "^[A-Za-z0-9]+[-_.A-Za-z0-9]+$" }, "uniqueItems": true }, "storage": { "type": "array", "minItems": 1, "items": { "oneOf": [ { "$ref": "#/definitions/diskDevice" }, { "$ref": "#/definitions/nfs" } ] } } }, "definitions": { "diskDevice": { "properties": { "type": { "enum": [ "disk" ] }, "mountpoint": { "type": "string" }, "size": { "type": "string", "pattern": "^[0-9]+ [MGT]B$" } }, "required": [ "type", "size" ], "additionalProperties": false }, "nfs": { "properties": { "type": { "enum": [ "nfs" ] }, "mountpoint": { "type": "string" }, "remotePath": { "type": "string" }, "server": { "type": "string", "pattern": "^[A-Za-z0-9]+[-_.A-Za-z0-9]+$" } }, "required": [ "type", "mountpoint", "server", "remotePath" ], "additionalProperties": false } }, "required": [ "cpus", "os", "memory", "storage", "names" ], "additionalProperties": false }'

mysql -u root project_centre --execute "INSERT INTO serviceschema (service_id, schema_version, schema_string) VALUES (1, 'v0.1', '${vm_service_schema}')"

mysql -u root project_centre --execute "DELETE from project_properties where propname in ('CNAME', 'CPUs', 'Disk Space', 'Memory', 'RAM', 'VM', 'VM01 CPUs', 'VM02 CPUs', 'VM02 RAM')"
