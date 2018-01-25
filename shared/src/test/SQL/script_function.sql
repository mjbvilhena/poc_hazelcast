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

BEGIN

         v_vehicle_id := NEW.registration_number;
         --raise exception '###LEVY: %',v_vehicle_id;
         INSERT INTO public.cdc(id, action_description, db_date, processed_to_cache, table_description, vehicle_id, cdc_date)
                     VALUES (nextval('cdc_id_seq'),TG_OP, current_timestamp, NULL, TG_TABLE_NAME, v_vehicle_id, current_timestamp);

    	 RETURN NEW;
END;

$BODY$;

ALTER FUNCTION public.log_vehicle_changes()
    OWNER TO postgres;





-- FUNCTION: public.log_vehicle_details_changes()

-- DROP FUNCTION public.log_vehicle_details_changes();

CREATE FUNCTION public.log_vehicle_details_changes()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
    ROWS 0
AS $BODY$

DECLARE v_db_registration_date timestamp without time zone;

BEGIN


     IF TG_OP = 'UPDATE' THEN
          SELECT registration_date INTO v_db_registration_date
            FROM vehicle
            JOIN vehicles_details ON vehicle_id = registration_number
           where registration_number = OLD.vehicle_id;

            INSERT INTO public.cdc(id, action_description, db_date, processed_to_cache, table_description, vehicle_id, cdc_date)
                        VALUES (nextval('cdc_id_seq'),TG_OP, v_db_registration_date, NULL, TG_TABLE_NAME, OLD.vehicle_id, current_timestamp);
     END IF;

     RETURN NEW;
END;

$BODY$;

ALTER FUNCTION public.log_vehicle_details_changes()
    OWNER TO postgres;

