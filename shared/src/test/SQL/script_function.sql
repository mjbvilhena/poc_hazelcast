-- FUNCTION: public.log_vehicle_changes()

-- DROP FUNCTION public.log_vehicle_changes();

CREATE OR REPLACE FUNCTION public.log_vehicle_changes()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF

AS $BODY$

DECLARE v_vehicle_id bigint;
DECLARE v_db_registration_date timestamp without time zone;

BEGIN
 SELECT vd.vehicle_id, v.registration_date INTO v_vehicle_id, v_db_registration_date
   FROM vehicle v
   JOIN vehicles_details vd ON vd.vehicle_id = v.registration_number
  where registration_number = OLD.registration_number;

 INSERT INTO public.cdc(id, action_description, db_date, processed_to_cache, table_description, vehicle_id)
					VALUES (nextval('cdc_id_seq'),TG_OP, v_db_registration_date, NULL, TG_TABLE_NAME, v_vehicle_id);
 RETURN NEW;
END;

$BODY$;

ALTER FUNCTION public.log_vehicle_changes()
    OWNER TO postgres;







-- FUNCTION: public.log_vehicle_insert_delete()

-- DROP FUNCTION public.log_vehicle_insert_delete();

CREATE OR REPLACE FUNCTION public.log_vehicle_insert_delete()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF

AS $BODY$

DECLARE v_vehicle_id bigint;
DECLARE v_db_registration_date timestamp without time zone;

BEGIN
 SELECT vd.vehicle_id, v.registration_date INTO v_vehicle_id, v_db_registration_date
   FROM vehicle v
   JOIN vehicles_details vd ON vd.vehicle_id = v.registration_number
  where registration_number = NEW.registration_number;

 INSERT INTO public.cdc(id, action_description, db_date, processed_to_cache, table_description, vehicle_id)
					VALUES (nextval('cdc_id_seq'),TG_OP, v_db_registration_date, NULL, TG_TABLE_NAME, v_vehicle_id);
 RETURN NEW;
END;

$BODY$;

ALTER FUNCTION public.log_vehicle_insert_delete()
    OWNER TO postgres;




