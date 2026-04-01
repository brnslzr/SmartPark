-- =========================
-- PARKING LOT
-- =========================
INSERT INTO parking_lot (lot_id, location, capacity, occupied_spaces, cost_per_minute)
VALUES ('LOT-1', 'SM North', 10, 1, 2.5);

-- =========================
-- VEHICLE
-- =========================
INSERT INTO vehicle (license_plate, vehicle_type, owner_name)
VALUES ('ABC-123', 'CAR', 'Juan Dela Cruz');

-- =========================
-- PARKING RECORD (ACTIVE)
-- =========================
INSERT INTO parking_record (id, vehicle_id, parking_lot_id, entry_time, status)
VALUES (
    1,
    (SELECT id FROM vehicle WHERE license_plate = 'ABC-123'),
    'LOT-1',
    CURRENT_TIMESTAMP,
    'PARKED'
);