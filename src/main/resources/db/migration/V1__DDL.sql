CREATE TABLE Users(
                      id SERIAL PRIMARY KEY,
                      username varchar(255) not null unique ,
                      password varchar(255) not null,
                      email varchar(255) not null unique,
                      role varchar(255)
);
CREATE TABLE Sensor(
                       id SERIAL PRIMARY KEY,
                       name varchar (30) not null,
                       model varchar(15) not null,
                       range_from BIGINT not null ,
                       range_to BIGINT not null,
                       type varchar(255) not null,
                       unit varchar(255),
                       location varchar(40),
                       description varchar(200)
);
CREATE TABLE MeasurementType(
                                id SERIAL PRIMARY KEY,
                                name varchar(255)
);
INSERT INTO MeasurementType (name) VALUES ('Pressure');
INSERT INTO MeasurementType (name) VALUES ('Voltage');
INSERT INTO MeasurementType (name) VALUES ('Temperature');
INSERT INTO MeasurementType (name) VALUES ('Humidity');

CREATE TABLE Unit(
                     id SERIAL PRIMARY KEY,
                     name varchar(255)
);
INSERT INTO Unit (name) VALUES ('bar');
INSERT INTO Unit (name) VALUES ('voltage');
INSERT INTO Unit (name) VALUES ('°С');
INSERT INTO Unit (name) VALUES ('%');

INSERT INTO Users (username, password, email, role)
VALUES ('Admin', '$2a$10$FKpXp61J0hNg5/hYoJisU.77ocCHVfqPKbSvXuLaWj.QBEG7BxVS6', 'admin@mail.com', 'ROLE_ADMIN');