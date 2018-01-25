-- Create the Tablespaces
-- as DB2admin run 'db2 -tvf 3_create_tablespace.sql
--
--CONNECT TO CWFODB;

CREATE BUFFERPOOL BUFF8K SIZE AUTOMATIC PAGESIZE 8192;
CREATE BUFFERPOOL BUFF16K SIZE AUTOMATIC PAGESIZE 16384;
create large tablespace TSD0_MDATA_4K  PAGESIZE 4K  managed by automatic storage bufferpool buff4k;
create large tablespace TSD0_MDATA_32K PAGESIZE 32K managed by automatic storage bufferpool buff32k;
create large tablespace TSI0_MDATA_4K  PAGESIZE 4K  managed by automatic storage bufferpool buff4k;
create large tablespace TSI0_MDATA_32K PAGESIZE 32K managed by automatic storage bufferpool buff32k;
create large tablespace TSL0_MDATA_4K  PAGESIZE 4K  managed by automatic storage bufferpool buff4k;
create large tablespace TSL0_MDATA_32K PAGESIZE 32K managed by automatic storage bufferpool buff32k;
CREATE BUFFERPOOL BUFF8K SIZE AUTOMATIC PAGESIZE 8192;
CREATE BUFFERPOOL BUFF16K SIZE AUTOMATIC PAGESIZE 16384;
CREATE SYSTEM TEMPORARY TABLESPACE VCTEMPTS_8K PAGESIZE 8K MANAGED BY AUTOMATIC STORAGE BUFFERPOOL buff8k;
CREATE SYSTEM TEMPORARY TABLESPACE VCTEMPTS_16K PAGESIZE 16K MANAGED BY AUTOMATIC STORAGE BUFFERPOOL buff16k;
