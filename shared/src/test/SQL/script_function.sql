-- FUNCTION: public.log_vehicle_changes()

-- DROP FUNCTION public.log_vehicle_changes();

CREATE FUNCTION public.log_vehicle_changes()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
    ROWS 0
AS $BODY$

DECLARE v_vehicle_id bigint;
DECLARE v_db_registration_date timestamp without time zone;

BEGIN
 SELECT vd.vehicle_id, v.registration_date INTO v_vehicle_id, v_db_registration_date
   FROM vehicle v
   JOIN vehicles_details vd ON vd.vehicle_id = v.registration_number
  where registration_number = NEW.registration_number;

  IF v_db_registration_date IS NULL then
  	v_db_registration_date := current_timestamp;
  END IF;

 INSERT INTO public.cdc(id, action_description, db_date, processed_to_cache, table_description, vehicle_id, cdc_date)
					VALUES (nextval('cdc_id_seq'),TG_OP, v_db_registration_date, NULL, TG_TABLE_NAME, NEW.registration_number, current_timestamp);
 RETURN NEW;
END;

$BODY$;

ALTER FUNCTION public.log_vehicle_changes()
    OWNER TO postgres;

