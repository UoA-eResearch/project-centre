# find all persons of a (top-level) faculty
select person_id from Person_affiliations where affiliations_KEY in
 (select id from division where top_div_id = 63);


# find all members of a department (that doesn't have any children)
select person_id from Person_affiliations where affiliations_KEY in ('67');


# find ids of all children of a division tree branch (manual)
SELECT Person_id
FROM Person_affiliations
WHERE affiliations_KEY IN ('66', '67');


# full tree hierarchy
SELECT
  t1.id AS lev1,
  t2.id AS lev2,
  t3.id AS lev3,
  t4.id AS lev4
FROM division AS t1
  LEFT JOIN division AS t2 ON t2.parent_div_id = t1.id
  LEFT JOIN division AS t3 ON t3.parent_div_id = t2.id
  LEFT JOIN division AS t4 ON t4.parent_div_id = t3.id
WHERE t1.parent_div_id IS NULL;

# full tree hierarchy for an end node with a max level of 4
SELECT
  t1.id AS lev1,
  t2.id AS lev2,
  t3.id AS lev3,
  t4.id AS lev4
FROM division AS t1
  LEFT JOIN division AS t2 ON t2.parent_div_id = t1.id
  LEFT JOIN division AS t3 ON t3.parent_div_id = t2.id
  LEFT JOIN division AS t4 ON t4.parent_div_id = t3.id
WHERE t1.parent_div_id IS NULL AND t4.id = 67;

# all leaf nodes
SELECT t1.id
FROM division AS t1 LEFT JOIN division AS t2
    ON t1.id = t2.parent_div_id
WHERE t2.id IS NULL;

# find all parent divisions of leaf node
SELECT t1.leaf_id
FROM (SELECT
        t1.id               AS leaf_id,
        @pv := t1.parent_div_id AS parent_id
      FROM (SELECT *
            FROM division
            ORDER BY id DESC) t1
        JOIN
        (SELECT @pv := 67) tmp
      WHERE t1.id = @pv) t1