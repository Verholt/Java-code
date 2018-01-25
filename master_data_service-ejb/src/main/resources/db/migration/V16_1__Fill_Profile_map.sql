DELETE
FROM "MDATA"."CO_PROFILE_KEY";


INSERT INTO "MDATA"."CO_PROFILE_KEY" ("KEY",
                                      "DESCRIPTION",
                                      "CREATED_AT",
                                      "CREATED_BY",
                                      "UPDATED_AT",
                                      "UPDATED_BY")
VALUES('E-mail','This is the e-mail key', CURRENT TIMESTAMP, 'sql import' ,
                                                             NULL,
                                                             NULL);


INSERT INTO "MDATA"."CO_PROFILE_KEY" ("KEY",
                                      "DESCRIPTION",
                                      "CREATED_AT",
                                      "CREATED_BY",
                                      "UPDATED_AT",
                                      "UPDATED_BY")
VALUES('Target TMS','This is the target TMS', CURRENT TIMESTAMP, 'sql import' ,
                                                                 NULL,
                                                                 NULL);


INSERT INTO "MDATA"."CO_PROFILE_KEY" ("KEY",
                                      "DESCRIPTION",
                                      "CREATED_AT",
                                      "CREATED_BY",
                                      "UPDATED_AT",
                                      "UPDATED_BY")
VALUES('CL-Profile','This is the CL-Profile', CURRENT TIMESTAMP, 'sql import' ,
                                                                 NULL,
                                                                 NULL);


INSERT INTO "MDATA"."CO_PROFILE_KEY" ("KEY",
                                      "DESCRIPTION",
                                      "CREATED_AT",
                                      "CREATED_BY",
                                      "UPDATED_AT",
                                      "UPDATED_BY")
VALUES('Country','This is the Country key', CURRENT TIMESTAMP, 'sql import' ,
                                                               NULL,
                                                               NULL);


INSERT INTO "MDATA"."CO_PROFILE_KEY" ("KEY",
                                      "DESCRIPTION",
                                      "CREATED_AT",
                                      "CREATED_BY",
                                      "UPDATED_AT",
                                      "UPDATED_BY")
VALUES('Department','This is the Department key', CURRENT TIMESTAMP, 'sql import' ,
                                                                     NULL,
                                                                     NULL);


INSERT INTO "MDATA"."CO_PROFILE_KEY" ("KEY",
                                      "DESCRIPTION",
                                      "CREATED_AT",
                                      "CREATED_BY",
                                      "UPDATED_AT",
                                      "UPDATED_BY")
VALUES('Traffic','This is the Traffic key', CURRENT TIMESTAMP, 'sql import' ,
                                                               NULL,
                                                               NULL);


DELETE
FROM "MDATA"."CO_PROFILE";


INSERT INTO "MDATA"."CO_PROFILE" ("NAME",
                                  "DESCRIPTION",
                                  "CREATED_AT",
                                  "CREATED_BY",
                                  "UPDATED_AT",
                                  "UPDATED_BY")
VALUES('CLDKHOR - Cargolink DK Horsens','This is the CLDKHOR - Cargolink DK Horsens', CURRENT TIMESTAMP, 'sql import' ,
                                                                                                         NULL,
                                                                                                         NULL);


INSERT INTO "MDATA"."CO_PROFILE" ("NAME",
                                  "DESCRIPTION",
                                  "CREATED_AT",
                                  "CREATED_BY",
                                  "UPDATED_AT",
                                  "UPDATED_BY")
VALUES('OMDKHOR - Cargolink and OM DK Horsens','This is the Cargolink and OM DK Horsens', CURRENT TIMESTAMP, 'sql import' ,
                                                                                                             NULL,
                                                                                                             NULL);


DELETE
FROM "MDATA"."CO_PROFILE_MAP";


INSERT INTO "MDATA"."CO_PROFILE_MAP" ("PROFILE_ID",
                                      "PROFILE_KEY_ID",
                                      "VALUE",
                                      "USAGE" ,
                                      "CREATED_AT",
                                      "CREATED_BY",
                                      "UPDATED_AT",
                                      "UPDATED_BY")
VALUES(
         (SELECT ID
          FROM "MDATA"."CO_PROFILE"
          WHERE NAME = 'CLDKHOR - Cargolink DK Horsens'),
         (SELECT ID
          FROM "MDATA"."CO_PROFILE_KEY"
          WHERE KEY = 'Target TMS'),'CargoLink','optional', CURRENT TIMESTAMP, 'sql import' ,
                                                                               NULL,
                                                                               NULL);


INSERT INTO "MDATA"."CO_PROFILE_MAP" ("PROFILE_ID",
                                      "PROFILE_KEY_ID",
                                      "VALUE",
                                      "USAGE" ,
                                      "CREATED_AT",
                                      "CREATED_BY",
                                      "UPDATED_AT",
                                      "UPDATED_BY")
VALUES(
         (SELECT ID
          FROM "MDATA"."CO_PROFILE"
          WHERE NAME = 'CLDKHOR - Cargolink DK Horsens'),
         (SELECT ID
          FROM "MDATA"."CO_PROFILE_KEY"
          WHERE KEY = 'CL-Profile'),'DKHOR','optional', CURRENT TIMESTAMP, 'sql import' ,
                                                                           NULL,
                                                                           NULL);


INSERT INTO "MDATA"."CO_PROFILE_MAP" ("PROFILE_ID",
                                      "PROFILE_KEY_ID",
                                      "VALUE",
                                      "USAGE" ,
                                      "CREATED_AT",
                                      "CREATED_BY",
                                      "UPDATED_AT",
                                      "UPDATED_BY")
VALUES(
         (SELECT ID
          FROM "MDATA"."CO_PROFILE"
          WHERE NAME = 'CLDKHOR - Cargolink DK Horsens'),
         (SELECT ID
          FROM "MDATA"."CO_PROFILE_KEY"
          WHERE KEY = 'E-mail'),'import@dk.dsv.com','optional', CURRENT TIMESTAMP, 'sql import' ,
                                                                                   NULL,
                                                                                   NULL);


INSERT INTO "MDATA"."CO_PROFILE_MAP" ("PROFILE_ID",
                                      "PROFILE_KEY_ID",
                                      "VALUE",
                                      "USAGE" ,
                                      "CREATED_AT",
                                      "CREATED_BY",
                                      "UPDATED_AT",
                                      "UPDATED_BY")
VALUES(
         (SELECT ID
          FROM "MDATA"."CO_PROFILE"
          WHERE NAME = 'OMDKHOR - Cargolink and OM DK Horsens'),
         (SELECT ID
          FROM "MDATA"."CO_PROFILE_KEY"
          WHERE KEY = 'Target TMS'),'Cargolink,OM','optional', CURRENT TIMESTAMP, 'sql import' ,
                                                                                  NULL,
                                                                                  NULL);


INSERT INTO "MDATA"."CO_PROFILE_MAP" ("PROFILE_ID",
                                      "PROFILE_KEY_ID",
                                      "VALUE",
                                      "USAGE" ,
                                      "CREATED_AT",
                                      "CREATED_BY",
                                      "UPDATED_AT",
                                      "UPDATED_BY")
VALUES(
         (SELECT ID
          FROM "MDATA"."CO_PROFILE"
          WHERE NAME = 'OMDKHOR - Cargolink and OM DK Horsens'),
         (SELECT ID
          FROM "MDATA"."CO_PROFILE_KEY"
          WHERE KEY = 'CL-Profile'),'DKHOR','optional', CURRENT TIMESTAMP, 'sql import' ,
                                                                           NULL,
                                                                           NULL);


INSERT INTO "MDATA"."CO_PROFILE_MAP" ("PROFILE_ID",
                                      "PROFILE_KEY_ID",
                                      "VALUE",
                                      "USAGE" ,
                                      "CREATED_AT",
                                      "CREATED_BY",
                                      "UPDATED_AT",
                                      "UPDATED_BY")
VALUES(
         (SELECT ID
          FROM "MDATA"."CO_PROFILE"
          WHERE NAME = 'OMDKHOR - Cargolink and OM DK Horsens'),
         (SELECT ID
          FROM "MDATA"."CO_PROFILE_KEY"
          WHERE KEY = 'Country'),'DK','optional', CURRENT TIMESTAMP, 'sql import' ,
                                                                     NULL,
                                                                     NULL);


INSERT INTO "MDATA"."CO_PROFILE_MAP" ("PROFILE_ID",
                                      "PROFILE_KEY_ID",
                                      "VALUE",
                                      "USAGE" ,
                                      "CREATED_AT",
                                      "CREATED_BY",
                                      "UPDATED_AT",
                                      "UPDATED_BY")
VALUES(
         (SELECT ID
          FROM "MDATA"."CO_PROFILE"
          WHERE NAME = 'OMDKHOR - Cargolink and OM DK Horsens'),
         (SELECT ID
          FROM "MDATA"."CO_PROFILE_KEY"
          WHERE KEY = 'Department'),'KDWBE','optional', CURRENT TIMESTAMP, 'sql import' ,
                                                                           NULL,
                                                                           NULL);


INSERT INTO "MDATA"."CO_PROFILE_MAP" ("PROFILE_ID",
                                      "PROFILE_KEY_ID",
                                      "VALUE",
                                      "USAGE" ,
                                      "CREATED_AT",
                                      "CREATED_BY",
                                      "UPDATED_AT",
                                      "UPDATED_BY")
VALUES(
         (SELECT ID
          FROM "MDATA"."CO_PROFILE"
          WHERE NAME = 'OMDKHOR - Cargolink and OM DK Horsens'),
         (SELECT ID
          FROM "MDATA"."CO_PROFILE_KEY"
          WHERE KEY = 'Traffic'),'DKBE57','optional', CURRENT TIMESTAMP, 'sql import' ,
                                                                         NULL,
                                                                         NULL);

