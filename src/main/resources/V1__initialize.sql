-- Dogs Table
CREATE TABLE Dogs (
    id INT PRIMARY KEY,
    dog_id LONG;
    name VARCHAR(255),
    url VARCHAR(500),
    age VARCHAR(50),
    gender VARCHAR(10),
    size VARCHAR(20),
    org_id INT,
    FOREIGN KEY (org_id) REFERENCES Organisation(org_id)
);

-- Breed Table
CREATE TABLE Breed (
    breed_id INT PRIMARY KEY,
    dog_id INT,
    primary_breed VARCHAR(255),
    secondary_breed VARCHAR(255),
    mixed BOOLEAN,
    not_known BOOLEAN,
    FOREIGN KEY (dog_id) REFERENCES Dogs(id)
);

-- Organisation Table
CREATE TABLE Organisation (
    id INT PRIMARY KEY,
    org_id VARCHAR(50),
    name VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(20),
    address1 VARCHAR(255),
    address2 VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    postcode VARCHAR(20),
    country VARCHAR(100)
);

-- Customer Table
CREATE TABLE Customer (
    customer_id INT PRIMARY KEY,
    name VARCHAR(255),
    phone_number VARCHAR(20)
);

-- Customer Interaction Table
CREATE TABLE Customer_Interaction (
    customer_id INT,
    dog_id INT,
    interaction_type ENUM('Liked', 'Disliked'),
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id),
    FOREIGN KEY (dog_id) REFERENCES Dogs(dog_id),
    PRIMARY KEY (customer_id, dog_id)
);
