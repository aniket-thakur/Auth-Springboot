CREATE TABLE booking (
  id BIGINT NOT NULL,
   created_at datetime NOT NULL,
   updated_at datetime NOT NULL,
   review_id BIGINT NULL,
   start_time datetime NULL,
   end_time datetime NULL,
   total_distance BIGINT NULL,
   booking_status VARCHAR(255) NULL,
   driver_id BIGINT NULL,
   passenger_id BIGINT NULL,
   CONSTRAINT pk_booking PRIMARY KEY (id)
);


CREATE TABLE driver (
  id BIGINT NOT NULL,
   created_at datetime NOT NULL,
   updated_at datetime NOT NULL,
   name VARCHAR(255) NOT NULL,
   license_number VARCHAR(255) NOT NULL,
   age INT NOT NULL,
   CONSTRAINT pk_driver PRIMARY KEY (id)
);



CREATE TABLE driver_review (
  id BIGINT NOT NULL,
   driver_review VARCHAR(255) NULL,
   CONSTRAINT pk_driverreview PRIMARY KEY (id)
);



CREATE TABLE passenger (
  id BIGINT NOT NULL,
   created_at datetime NOT NULL,
   updated_at datetime NOT NULL,
   name VARCHAR(255) NOT NULL,
   CONSTRAINT pk_passenger PRIMARY KEY (id)
);

CREATE TABLE booking_review (
  id BIGINT NOT NULL,
   created_at datetime NOT NULL,
   updated_at datetime NOT NULL,
   content VARCHAR(255) NOT NULL,
   rating DOUBLE NULL,
   CONSTRAINT pk_booking_review PRIMARY KEY (id)
);



ALTER TABLE booking ADD CONSTRAINT FK_BOOKING_ON_PASSENGER FOREIGN KEY (passenger_id) REFERENCES passenger (id);

ALTER TABLE booking ADD CONSTRAINT FK_BOOKING_ON_REVIEW FOREIGN KEY (review_id) REFERENCES booking_review (id);

ALTER TABLE driver ADD CONSTRAINT uc_driver_license_number UNIQUE (license_number);

ALTER TABLE driver_review ADD CONSTRAINT FK_DRIVERREVIEW_ON_ID FOREIGN KEY (id) REFERENCES booking_review (id);

ALTER TABLE booking ADD CONSTRAINT FK_BOOKING_ON_DRIVER FOREIGN KEY (driver_id) REFERENCES driver (id);