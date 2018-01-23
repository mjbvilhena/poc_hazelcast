-- Trigger: after_vehicle_changes

-- DROP TRIGGER after_vehicle_changes ON public.vehicle;

CREATE TRIGGER after_vehicle_changes
    AFTER INSERT OR DELETE OR UPDATE
    ON public.vehicle
    FOR EACH ROW
    EXECUTE PROCEDURE public.log_vehicle_insert_delete();


-- Trigger: after_vehicles_details_changes

-- DROP TRIGGER after_vehicles_details_changes ON public.vehicles_details;

CREATE TRIGGER after_vehicles_details_changes
    AFTER INSERT OR DELETE OR UPDATE
    ON public.vehicles_details
    FOR EACH ROW
    EXECUTE PROCEDURE public.log_vehicle_insert_delete();


