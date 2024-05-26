CREATE TABLE Users(
                      id SERIAL PRIMARY KEY,
                      username varchar(255) not null unique ,
                      password varchar(255) not null,
                      email varchar(255) not null unique,
                      role varchar(255)
);
CREATE TABLE Sensor(
                       id SERIAL PRIMARY KEY,
                       name varchar (255) not null,
                       model varchar(255) not null,
                       range_from BIGINT,
                       range_to BIGINT,
                       type varchar(255),
                       unit varchar(255),
                       location varchar(255),
                       description varchar(410) not null
);
CREATE TABLE MeasurementType(
                                id SERIAL PRIMARY KEY,
                                name varchar(255)
);
INSERT INTO MeasurementType (name) VALUES ('Pressure');
INSERT INTO MeasurementType (name) VALUES ('Voltage');
INSERT INTO MeasurementType (name) VALUES ('Temperature');
INSERT INTO MeasurementType (name) VALUES ('Humidity');
INSERT INTO Users (username, password, email, role)
VALUES ('Admin', '$2a$10$FKpXp61J0hNg5/hYoJisU.77ocCHVfqPKbSvXuLaWj.QBEG7BxVS6', 'admin@mail.com', 'ROLE_ADMIN');