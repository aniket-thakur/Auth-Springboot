ALTER TABLE `booking`
  DROP FOREIGN KEY `FK_BOOKING_ON_REVIEW`;  -- Use the correct FK name here
 -- 2. Remove the review_id column
 ALTER TABLE `booking`
   DROP COLUMN `review_id`;

-- -- 3. Add a new booking_id column to the booking table
 ALTER TABLE `booking_review`
   ADD COLUMN `booking_id` bigint DEFAULT NULL;
--
ALTER TABLE booking_review
ADD CONSTRAINT `FK_BOOKING_ON_REVIEW_TABLE` FOREIGN key(booking_id)
REFERENCES booking(id);

