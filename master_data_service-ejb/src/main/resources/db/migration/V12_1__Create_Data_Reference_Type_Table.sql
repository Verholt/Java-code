--<ScriptOptions statementTerminator=";"/>

Delete from "MDATA"."REFERENCE_TYPE";
insert into "MDATA"."REFERENCE_TYPE" ("REFERENCE_TYPE", "IS_DEFAULT", "CREATED_AT", "CREATED_BY", "UPDATED_AT", "UPDATED_BY") values('Shipper reference', 1, current timestamp, 'sql import' , NULL, NULL);
insert into "MDATA"."REFERENCE_TYPE" ("REFERENCE_TYPE", "IS_DEFAULT", "CREATED_AT", "CREATED_BY", "UPDATED_AT", "UPDATED_BY") values('Consignee reference', 1, current timestamp, 'sql import' , NULL, NULL);
insert into "MDATA"."REFERENCE_TYPE" ("REFERENCE_TYPE", "IS_DEFAULT", "CREATED_AT", "CREATED_BY", "UPDATED_AT", "UPDATED_BY") values('Invoicing reference', 1, current timestamp, 'sql import' , NULL, NULL);
insert into "MDATA"."REFERENCE_TYPE" ("REFERENCE_TYPE", "IS_DEFAULT", "CREATED_AT", "CREATED_BY", "UPDATED_AT", "UPDATED_BY") values('Other', 1, current timestamp, 'sql import' , NULL, NULL);
