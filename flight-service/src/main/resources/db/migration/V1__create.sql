CREATE TABLE IF NOT EXISTS airports(
    id UUID PRIMARY KEY,
    name VARCHAR(40) UNIQUE NOT NULL,
    description VARCHAR(255),
    address VARCHAR(255) NOT NULL
);


CREATE TABLE IF NOT EXISTS flights(
    id UUID PRIMARY KEY,
    departure_airport_id UUID NOT NULL,
    arrival_airport_id UUID NOT NULL,
    plane_id UUID NOT NULL,
    departure_date_time TIMESTAMP,
    arrival_date_time TIMESTAMP,
    departure_gate_number VARCHAR(40) NOT NULL,
    arrival_gate_number VARCHAR(40) NOT NULL,
    price INTEGER
);

CREATE TABLE IF NOT EXISTS planes (
    id UUID PRIMARY KEY,
    model VARCHAR(40) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS seats (
    id UUID PRIMARY KEY,
    plane_id UUID NOT NULL,
    seatType VARCHAR(40) NOT NULL,
    isOccupied BOOLEAN DEFAULT TRUE
);