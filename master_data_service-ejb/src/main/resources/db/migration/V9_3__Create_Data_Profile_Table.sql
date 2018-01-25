--<ScriptOptions statementTerminator=";"/>

Delete from "MDATA"."CO_PROFILE_KEY";
insert into "MDATA"."CO_PROFILE_KEY" ("KEY", "DESCRIPTION", "CREATED_AT", "CREATED_BY", "UPDATED_AT", "UPDATED_BY") values('E-mail','This is the e-mail key', current timestamp, 'sql import' , NULL, NULL);
insert into "MDATA"."CO_PROFILE_KEY" ("KEY", "DESCRIPTION", "CREATED_AT", "CREATED_BY", "UPDATED_AT", "UPDATED_BY") values('Target TMS','This is the target TMS', current timestamp, 'sql import' , NULL, NULL);
insert into "MDATA"."CO_PROFILE_KEY" ("KEY", "DESCRIPTION", "CREATED_AT", "CREATED_BY", "UPDATED_AT", "UPDATED_BY") values('CL-Profile','This is the CL-Profile', current timestamp, 'sql import' , NULL, NULL);
insert into "MDATA"."CO_PROFILE_KEY" ("KEY", "DESCRIPTION", "CREATED_AT", "CREATED_BY", "UPDATED_AT", "UPDATED_BY") values('Country','This is the Country key', current timestamp, 'sql import' , NULL, NULL);
insert into "MDATA"."CO_PROFILE_KEY" ("KEY", "DESCRIPTION", "CREATED_AT", "CREATED_BY", "UPDATED_AT", "UPDATED_BY") values('Department','This is the Department key', current timestamp, 'sql import' , NULL, NULL);
insert into "MDATA"."CO_PROFILE_KEY" ("KEY", "DESCRIPTION", "CREATED_AT", "CREATED_BY", "UPDATED_AT", "UPDATED_BY") values('Traffic','This is the Traffic key', current timestamp, 'sql import' , NULL, NULL);


Delete from "MDATA"."CO_PROFILE";
insert into "MDATA"."CO_PROFILE" ("NAME", "DESCRIPTION", "CREATED_AT", "CREATED_BY", "UPDATED_AT", "UPDATED_BY") values('CLDKHOR - Cargolink DK Horsens','This is the CLDKHOR - Cargolink DK Horsens', current timestamp, 'sql import' , NULL, NULL);
insert into "MDATA"."CO_PROFILE" ("NAME", "DESCRIPTION", "CREATED_AT", "CREATED_BY", "UPDATED_AT", "UPDATED_BY") values('OMDKHOR - Cargolink and OM DK Horsens','This is the Cargolink and OM DK Horsens', current timestamp, 'sql import' , NULL, NULL);

Delete from "MDATA"."CO_PROFILE_MAP";
insert into "MDATA"."CO_PROFILE_MAP" ("PROFILE_ID", "KEY_ID", "VALUE", "USAGE" , "CREATED_AT", "CREATED_BY", "UPDATED_AT", "UPDATED_BY") values((select ID from "MDATA"."CO_PROFILE" where NAME = 'CLDKHOR - Cargolink DK Horsens'),(select ID from "MDATA"."CO_PROFILE_KEY" where KEY = 'Target TMS'),'CargoLink','optional', current timestamp, 'sql import' , NULL, NULL);
insert into "MDATA"."CO_PROFILE_MAP" ("PROFILE_ID", "KEY_ID", "VALUE", "USAGE" , "CREATED_AT", "CREATED_BY", "UPDATED_AT", "UPDATED_BY") values((select ID from "MDATA"."CO_PROFILE" where NAME = 'CLDKHOR - Cargolink DK Horsens'),(select ID from "MDATA"."CO_PROFILE_KEY" where KEY = 'CL-Profile'),'DKHOR','optional', current timestamp, 'sql import' , NULL, NULL);
insert into "MDATA"."CO_PROFILE_MAP" ("PROFILE_ID", "KEY_ID", "VALUE", "USAGE" , "CREATED_AT", "CREATED_BY", "UPDATED_AT", "UPDATED_BY") values((select ID from "MDATA"."CO_PROFILE" where NAME = 'CLDKHOR - Cargolink DK Horsens'),(select ID from "MDATA"."CO_PROFILE_KEY" where KEY = 'E-mail'),'import@dk.dsv.com','optional', current timestamp, 'sql import' , NULL, NULL);

insert into "MDATA"."CO_PROFILE_MAP" ("PROFILE_ID", "KEY_ID", "VALUE", "USAGE" , "CREATED_AT", "CREATED_BY", "UPDATED_AT", "UPDATED_BY") values((select ID from "MDATA"."CO_PROFILE" where NAME = 'OMDKHOR - Cargolink and OM DK Horsens'),(select ID from "MDATA"."CO_PROFILE_KEY" where KEY = 'Target TMS'),'Cargolink,OM','optional', current timestamp, 'sql import' , NULL, NULL);
insert into "MDATA"."CO_PROFILE_MAP" ("PROFILE_ID", "KEY_ID", "VALUE", "USAGE" , "CREATED_AT", "CREATED_BY", "UPDATED_AT", "UPDATED_BY") values((select ID from "MDATA"."CO_PROFILE" where NAME = 'OMDKHOR - Cargolink and OM DK Horsens'),(select ID from "MDATA"."CO_PROFILE_KEY" where KEY = 'CL-Profile'),'DKHOR','optional', current timestamp, 'sql import' , NULL, NULL);
insert into "MDATA"."CO_PROFILE_MAP" ("PROFILE_ID", "KEY_ID", "VALUE", "USAGE" , "CREATED_AT", "CREATED_BY", "UPDATED_AT", "UPDATED_BY") values((select ID from "MDATA"."CO_PROFILE" where NAME = 'OMDKHOR - Cargolink and OM DK Horsens'),(select ID from "MDATA"."CO_PROFILE_KEY" where KEY = 'Country'),'DK','optional', current timestamp, 'sql import' , NULL, NULL);
insert into "MDATA"."CO_PROFILE_MAP" ("PROFILE_ID", "KEY_ID", "VALUE", "USAGE" , "CREATED_AT", "CREATED_BY", "UPDATED_AT", "UPDATED_BY") values((select ID from "MDATA"."CO_PROFILE" where NAME = 'OMDKHOR - Cargolink and OM DK Horsens'),(select ID from "MDATA"."CO_PROFILE_KEY" where KEY = 'Department'),'KDWBE','optional', current timestamp, 'sql import' , NULL, NULL);
insert into "MDATA"."CO_PROFILE_MAP" ("PROFILE_ID", "KEY_ID", "VALUE", "USAGE" , "CREATED_AT", "CREATED_BY", "UPDATED_AT", "UPDATED_BY") values((select ID from "MDATA"."CO_PROFILE" where NAME = 'OMDKHOR - Cargolink and OM DK Horsens'),(select ID from "MDATA"."CO_PROFILE_KEY" where KEY = 'Traffic'),'DKBE57','optional', current timestamp, 'sql import' , NULL, NULL);
