--<ScriptOptions statementTerminator=";"/>

Delete from "MDATA"."REFERENCE_TYPE";
insert into "MDATA"."REFERENCE_TYPE" ("REFERENCE_TYPE", "IS_DEFAULT", "CREATED_AT", "CREATED_BY", "UPDATED_AT", "UPDATED_BY") values('Shipper', 1, current timestamp, 'sql import' , NULL, NULL);
insert into "MDATA"."REFERENCE_TYPE" ("REFERENCE_TYPE", "IS_DEFAULT", "CREATED_AT", "CREATED_BY", "UPDATED_AT", "UPDATED_BY") values('Consignee', 1, current timestamp, 'sql import' , NULL, NULL);
insert into "MDATA"."REFERENCE_TYPE" ("REFERENCE_TYPE", "IS_DEFAULT", "CREATED_AT", "CREATED_BY", "UPDATED_AT", "UPDATED_BY") values('Invoicing', 1, current timestamp, 'sql import' , NULL, NULL);
insert into "MDATA"."REFERENCE_TYPE" ("REFERENCE_TYPE", "IS_DEFAULT", "CREATED_AT", "CREATED_BY", "UPDATED_AT", "UPDATED_BY") values('Other', 1, current timestamp, 'sql import' , NULL, NULL);
insert into "MDATA"."REFERENCE_TYPE" ("REFERENCE_TYPE", "IS_DEFAULT", "CREATED_AT", "CREATED_BY", "UPDATED_AT", "UPDATED_BY") values('Pickup', 0, current timestamp, 'sql import' , NULL, NULL);
insert into "MDATA"."REFERENCE_TYPE" ("REFERENCE_TYPE", "IS_DEFAULT", "CREATED_AT", "CREATED_BY", "UPDATED_AT", "UPDATED_BY") values('Delivery', 0, current timestamp, 'sql import' , NULL, NULL);
insert into "MDATA"."REFERENCE_TYPE" ("REFERENCE_TYPE", "IS_DEFAULT", "CREATED_AT", "CREATED_BY", "UPDATED_AT", "UPDATED_BY") values('Freight payer', 0, current timestamp, 'sql import' , NULL, NULL);
insert into "MDATA"."REFERENCE_TYPE" ("REFERENCE_TYPE", "IS_DEFAULT", "CREATED_AT", "CREATED_BY", "UPDATED_AT", "UPDATED_BY") values('Document', 0, current timestamp, 'sql import' , NULL, NULL);
