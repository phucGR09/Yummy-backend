-- Create USER table
CREATE TABLE USERS (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(50) NOT NULL,
    full_name VARCHAR(50),
    phone_number VARCHAR(50) UNIQUE,
    user_type ENUM('CUSTOMER', 'RESTAURANT_OWNER', 'ADMIN', 'DELIVERY_DRIVER') NOT NULL
);

-- Create RESTAURANT_OWNER table
CREATE TABLE RESTAURANT_OWNERS (
    owner_id INT PRIMARY KEY,
    tax_number VARCHAR(50) UNIQUE,
    FOREIGN KEY (owner_id) REFERENCES USERS(user_id)
);

-- Create ADMINISTRATOR table
CREATE TABLE ADMINISTRATORS (
    admin_id INT PRIMARY KEY,
    FOREIGN KEY (admin_id) REFERENCES USERS(user_id)
);

-- Create DELIVERY_DRIVER table
CREATE TABLE DELIVERY_DRIVERS (
    driver_id INT PRIMARY KEY,
    avatar_url VARCHAR(500),
    license_plate VARCHAR(50) UNIQUE,
    identity_number VARCHAR(50) UNIQUE,
    FOREIGN KEY (driver_id) REFERENCES USERS(user_id)
);

-- Create CUSTOMER table
CREATE TABLE CUSTOMERS (
    customer_id INT PRIMARY KEY,
    address VARCHAR(500),
    FOREIGN KEY (customer_id) REFERENCES USERS(user_id)
);

-- Create RESTAURANT table
CREATE TABLE RESTAURANTS (
    restaurant_id INT AUTO_INCREMENT PRIMARY KEY,
    owner_id INT,
    name VARCHAR(50) NOT NULL,
    address VARCHAR(50),
    opening_hours TIME,
    FOREIGN KEY (owner_id) REFERENCES RESTAURANT_OWNERS(owner_id),
    UNIQUE (owner_id, name)
);

-- Create MENU_ITEM table
CREATE TABLE MENU_ITEMS (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    restaurant_id INT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(1000),
    price DECIMAL(10,2) NOT NULL,
    image_url VARCHAR(500),
    FOREIGN KEY (restaurant_id) REFERENCES RESTAURANTS(restaurant_id),
    UNIQUE (restaurant_id, name)
);

-- Create ORDER table
CREATE TABLE ORDERS (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    restaurant_id INT,
    driver_id INT,
    customer_id INT,
    order_time DATETIME,
    total_price DECIMAL(10,2),
    order_status ENUM('WAITING_CONFIRMATION', 'CONFIRMED', 'WAITING_DELIVERY', 'DELIVERING', 'DELIVERED', 'CANCELLED'),
    FOREIGN KEY (restaurant_id) REFERENCES RESTAURANTS(restaurant_id),
    FOREIGN KEY (driver_id) REFERENCES DELIVERY_DRIVERS(driver_id),
    FOREIGN KEY (customer_id) REFERENCES CUSTOMERS(customer_id)
);

-- Create ORDER_MENU_ITEM table (junction table for order-item relationship)
CREATE TABLE ORDER_MENU_ITEMS (
    order_item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    item_id INT,
    quantity INT NOT NULL,
    special_instructions VARCHAR(1000),
    FOREIGN KEY (order_id) REFERENCES ORDERS(order_id),
    FOREIGN KEY (item_id) REFERENCES MENU_ITEMS(item_id)
);

-- Create REVIEW table
CREATE TABLE REVIEWS (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    reviewer_id INT,
    rating INT,
    comment VARCHAR(1000),
    review_time DATETIME,
    FOREIGN KEY (order_id) REFERENCES ORDERS(order_id),
    FOREIGN KEY (reviewer_id) REFERENCES USERS(user_id)
);

-- Create REPORT table
CREATE TABLE REPORTS (
    complaint_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    user_id INT,
    description VARCHAR(1000),
    complaint_status ENUM('WAITING_PROGRESS', 'IN_PROGRESS', 'COMPLETED', 'DENIED'),
    resolution_details VARCHAR(1000),
    complaint_time DATETIME,
    FOREIGN KEY (order_id) REFERENCES ORDERS(order_id),
    FOREIGN KEY (user_id) REFERENCES USERS(user_id)
);