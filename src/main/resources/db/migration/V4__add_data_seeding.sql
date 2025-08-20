INSERT INTO RESTAURANT_OWNERS (owner_id, tax_number) VALUES
(3, 'TAX12345'),
(4, 'TAX12346'),
(11, 'TAX12347'),
(16, 'TAX12348'),
(20, 'TAX12349');

INSERT INTO ADMINISTRATORS (admin_id) VALUES
(5),
(6),
(13);

INSERT INTO DELIVERY_DRIVERS (driver_id, avatar_url, license_plate, identity_number) VALUES
(7, 'https://example.com/avatar1.jpg', 'AB123CD', 'ID001'),
(8, 'https://example.com/avatar2.jpg', 'EF456GH', 'ID002'),
(12, 'https://example.com/avatar3.jpg', 'IJ789KL', 'ID003'),
(17, 'https://example.com/avatar5.jpg', 'QR345ST', 'ID005');

INSERT INTO CUSTOMERS (customer_id, address) VALUES
(1, '123 Main St, Cityville, 12345'),
(2, '456 Oak Rd, Townsville, 23456'),
(9, '789 Pine Ln, Villageville, 34567'),
(10, '101 Maple Dr, Suburbia, 45678'),
(14, '202 Birch Ave, Metrocity, 56789'),
(15, '303 Cedar St, Bigcity, 67890'),
(18, '404 Elm Blvd, Countrytown, 78901'),
(19, '505 Redwood Way, Seaside, 89012');

INSERT INTO RESTAURANTS (owner_id, name, address, opening_hours) VALUES
(3, 'Pizza Paradise', '123 Food St, Cityville', '11:00:00'),
(4, 'Burger Haven', '456 Grub Rd, Townsville', '10:00:00'),
(11, 'Pasta Express', '789 Italian Rd, Villageville', '12:00:00'),
(16, 'Sushi World', '101 Ocean Blvd, Suburbia', '14:00:00'),
(20, 'Taco Fiesta', '202 Fiesta Ave, Metrocity', '09:00:00');

INSERT INTO MENU_ITEMS (restaurant_id, name, description, price, image_url) VALUES
(1, 'Bánh tráng trộn', 'Bánh tráng trộn với trứng cút và hành phi', 25000, 'https://example.com/margherita.jpg'),
(1, 'Matcha latte', 'Latte but with matcha', 55000, 'https://example.com/pepperoni.jpg'),
(2, 'Bánh tráng trộn', 'Bánh tráng trộn với trứng cút và hành phi', 25000, 'https://example.com/cheeseburger.jpg'),
(2, 'Matcha latte', 'Latte but with matcha', 55000, 'https://example.com/veggieburger.jpg'),
(3, 'Bánh tráng trộn', 'Bánh tráng trộn với trứng cút và hành phi', 25000, 'https://example.com/spaghetti.jpg'),
(3, 'Matcha latte', 'Latte but with matcha', 55000, 'https://example.com/lasagna.jpg'),
(4, 'Latte', 'Tea latte', 45000, 'https://example.com/californiaroll.jpg'),
(4, 'Matcha latte', 'Latte but with matcha', 55000, 'https://example.com/salmon.jpg'),
(5, 'Bánh tráng trộn', 'Bánh tráng trộn với trứng cút và hành phi', 25000, 'https://example.com/beeftacos.jpg'),
(5, 'Latte', 'Tea latte', 45000, 'https://example.com/chickenburrito.jpg');

INSERT INTO ORDERS (restaurant_id, driver_id, customer_id, order_time, total_price, order_status) VALUES
(1, 7, 1, '2025-01-11 12:00:00', 21.98, 'WAITING_RESTAURANT_CONFIRMATION'),
(2, 8, 2, '2025-01-11 12:30:00', 18.98, 'RESTAURANT_CONFIRMED'),
(3, 12, 9, '2025-01-11 13:00:00', 27.98, 'WAITING_DRIVER_CONFIRMATION'),
(4, 7, 10, '2025-01-11 13:30:00', 32.98, 'DRIVER_CONFIRMED'),
(5, 17, 14, '2025-01-11 14:00:00', 15.98, 'WAITING_DELIVERY'),
(1, 7, 15, '2025-01-11 14:30:00', 19.98, 'DELIVERING'),
(2, 8, 18, '2025-01-11 15:00:00', 16.98, 'DELIVERED'),
(3, 12, 19, '2025-01-11 15:30:00', 21.98, 'CANCELLED'),
(4, 8, 2, '2025-01-11 16:00:00', 18.98, 'WAITING_RESTAURANT_CONFIRMATION'),
(5, 17, 9, '2025-01-11 16:30:00', 22.98, 'RESTAURANT_CONFIRMED');

INSERT INTO ORDER_MENU_ITEMS (order_id, item_id, quantity, special_instructions) VALUES
(1, 1, 2, 'Extra cheese'),
(2, 3, 2, 'No onions'),
(3, 5, 2, 'Add mushrooms'),
(4, 7, 1, 'Hold the soy sauce'),
(5, 9, 2, 'No lettuce'),
(6, 1, 1, 'Extra sauce'),
(7, 2, 1, 'No pickles'),
(8, 4, 2, 'Add extra cheese'),
(9, 6, 1, 'No garlic'),
(10, 8, 1, 'Spicy sauce');

INSERT INTO REVIEWS (order_id, reviewer_id, rating, comment, review_time) VALUES
(1, 1, 5, 'Great pizza!', '2025-01-11 13:00:00'),
(2, 2, 4, 'Burger was delicious, but the fries were cold.', '2025-01-11 13:30:00'),
(3, 3, 5, 'Pasta was perfect! Will order again.', '2025-01-11 14:00:00'),
(4, 4, 3, 'Sushi was fresh, but a bit too salty.', '2025-01-11 14:30:00'),
(5, 5, 5, 'Tacos were amazing!', '2025-01-11 15:00:00'),
(6, 6, 4, 'The pizza was good, but a little too greasy.', '2025-01-11 15:30:00'),
(7, 7, 5, 'Sashimi was fantastic! Highly recommend.', '2025-01-11 16:00:00'),
(8, 8, 4, 'Burrito was good, but could use more chicken.', '2025-01-11 16:30:00'),
(9, 9, 2, 'Food arrived cold, not happy with the service.', '2025-01-11 17:00:00'),
(10, 10, 5, 'Everything was perfect, very satisfied!', '2025-01-11 17:30:00');

INSERT INTO REPORTS (order_id, user_id, description, complaint_status, resolution_details, complaint_time) VALUES
(1, 1, 'The pizza was slightly overcooked.', 'WAITING_PROGRESS', NULL, '2025-01-11 13:30:00'),
(2, 2, 'Fries were cold.', 'IN_PROGRESS', 'Refund issued for fries', '2025-01-11 14:00:00');
