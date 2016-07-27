#!/bin/bash

#mysql -u root --execute 'drop database project_centre; create database project_centre'
#mysql -u root eresearch < ./eresearch.schema
mysql -h mysql-bg.ceres.auckland.ac.nz -u uoaeresearch -puoaeresearch project_centre < ./mysql.schema
python migrate_basics.py
