-- Trigger: after_vehicle_changes

-- DROP TRIGGER after_vehicle_changes ON public.vehicle;

CREATE TRIGGER after_vehicle_changes
    AFTER INSERT OR DELETE OR UPDATE
    ON public.vehicle
    FOR EACH ROW
    EXECUTE PROCEDURE public.log_vehicle_changes();