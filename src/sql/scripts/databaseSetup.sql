DROP TABLE Ratings;
DROP TABLE Review;
DROP TABLE Restaurant;
DROP TABLE Hotel;
DROP TABLE Amenities;
DROP TABLE Information_Offers;
DROP TABLE Event_Offers;
DROP TABLE Toursite_Located;
DROP TABLE Location_IsIn;
DROP TABLE Country;


CREATE TABLE Country(
                        name VARCHAR(50)
                            PRIMARY KEY
);


INSERT INTO Country VALUES('Canada');
INSERT INTO Country VALUES ('United States');
INSERT INTO Country VALUES ('Germany');
INSERT INTO Country VALUES ('Italy');
INSERT INTO Country VALUES ('Egypt');



CREATE TABLE Location_IsIn(
                              cname VARCHAR(50),
                              lname VARCHAR(50),
                              lon DECIMAL,
                              lat DECIMAL,
                              PRIMARY KEY(lon,lat),
                              FOREIGN KEY(cname) REFERENCES Country(name) ON DELETE CASCADE
);


INSERT INTO Location_IsIn VALUES ('Canada', 'Vancouver', 49.2827, -123.1207);
INSERT INTO Location_IsIn VALUES ('United States', 'New York', 40.7128, -74.0060);
INSERT INTO Location_IsIn VALUES ('Germany', 'Berlin', 52.5200, 13.4050);
INSERT INTO Location_IsIn VALUES ('Italy', 'Venice',  45.4408, 12.3155);
INSERT INTO Location_IsIn VALUES ('Egypt', 'Cairo', 30.0444, 31.2357);
INSERT INTO Location_IsIn VALUES ('Canada', 'Yellowknife', 62.2713, -114.2212);
INSERT INTO Location_IsIn VALUES ('Germany', 'Frankfurt', 50.1109, 8.6821);
INSERT INTO Location_IsIn VALUES ('Canada', 'Toronto', 43.6510, -79.3470);




CREATE TABLE Toursite_Located(
                                 address VARCHAR(50),
                                 climate VARCHAR(50),
                                 lon DECIMAL NOT NULL,
                                 lat DECIMAL NOT NULL,
                                 siteID INT PRIMARY KEY,
                                 name VARCHAR(50),
                                 fee DECIMAL,
                                 FOREIGN KEY(lon, lat) REFERENCES Location_IsIn(lon, lat) ON DELETE CASCADE
);


INSERT INTO Toursite_Located
VALUES ('1055 Canada Pl, Vancouver, BC, Canada', 'Temperate Oceanic' , 49.2827, -123.1207, 0001, 'Vancouver Convention Centre', 14.00);
INSERT INTO Toursite_Located
VALUES('Grand Canyon Village, AZ 86023, USA', 'Dry', 40.7128, -74.0060, 0002, 'Grand Canyon National Park', 20.00);
INSERT INTO Toursite_Located
VALUES('Piazza del Colosseo, 1, 00184 Roma RM, Italy', 'Mediterranean Climate', 52.5200, 13.4050, 0003, 'Colosseum', 20.00);
INSERT INTO Toursite_Located
VALUES('Machu Picchu, Aguas Calientes, Peru', 'Mild Temperature', 45.4408, 12.3155, 0004, 'Machu Picchu', 45.00);
INSERT INTO Toursite_Located
VALUES('Queensland, Australia', 'Tropical', 30.0444, 31.2357, 0005, 'The Great Barrier Reef', 70.00);


CREATE TABLE Event_Offers(
                             startDate VARCHAR(50),
                             endDate VARCHAR(50),
                             duration DECIMAL NOT NULL,
                             eventID INT PRIMARY KEY,
                             siteID INT NOT NULL,
                             name VARCHAR(50),
                             FOREIGN KEY(siteID) REFERENCES Toursite_Located(siteID) ON DELETE CASCADE
);


INSERT INTO Event_Offers VALUES ('Mar 1 2024', 'Mar 2 2024', 24, 1001, 0001, 'Vancouver Bike Show 2024');
INSERT INTO Event_Offers VALUES ('Jun 6 2024', 'Jun 9 2024', 72, 1002, 0002, 'Grand Canyon Rim and Raft Tour');
INSERT INTO Event_Offers VALUES ('Jun 28 2024', 'Jun 28 2024', 3, 1003, 0003, 'Simple Minds');
INSERT INTO Event_Offers VALUES('July 1 2024', 'July 1 2024', 5, 1004, 0004, 'Machu Picchu Anniversary');
INSERT INTO Event_Offers VALUES('July 4 2024', 'July 4 2024', 12, 1005, 0005, 'Australia Day');
INSERT INTO Event_Offers VALUES('July 9 2024', 'July 9 2024', 12, 1006, 0002, 'US Party Day');
INSERT INTO Event_Offers VALUES('July 1 2024', 'July 1 2024', 12, 1007, 0001, 'Canada Day');



CREATE TABLE Information_Offers(
                                   InfoID INT PRIMARY KEY,
                                   category VARCHAR(50),
                                   visitors INT,
                                   description VARCHAR(50),
                                   siteID INT NOT NULL,
                                   eventID INT NOT NULL,
                                   FOREIGN KEY(siteID) REFERENCES Toursite_Located(siteID) ON DELETE CASCADE,
                                   FOREIGN KEY(eventID) REFERENCES Event_Offers(eventID) ON DELETE CASCADE
);

INSERT INTO Information_Offers VALUES (2001, 'Outdoor Activity', 1423, 'Western Canadas Ultimate Cycling Event', 0001, 1001);
INSERT INTO Information_Offers VALUES (2002, 'Outdoor Activity', 10, 'Outdoor activities in Grand Canyon', 0002, 1002);
INSERT INTO Information_Offers VALUES (2003, 'Festival', 12000, 'Roma Summer Festival 2024', 0003, 1003);
INSERT INTO Information_Offers VALUES (2004, 'Celebration', 2000, 'Celebration for Machu Picchu', 0004, 1004);
INSERT INTO Information_Offers VALUES (2005, 'Celebration', 5000, 'Celebration for Australia Day', 0005, 1005);
INSERT INTO Information_Offers VALUES (2006, 'Festival', 5000, 'Festival in the CAD', 0001, 1005);
INSERT INTO Information_Offers VALUES (2007, 'Celebration', 5000, 'Celebration of Canada Day', 0001, 1007);
INSERT INTO Information_Offers VALUES (2008, 'Celebration', 5000, 'Celebration of Italy Day', 0003, 1005);
INSERT INTO Information_Offers VALUES (2009, 'Festival', 5000, 'Party in the USA', 0002, 1006);


CREATE TABLE Amenities(
                          AmID INT PRIMARY KEY,
                          name VARCHAR(50) UNIQUE,
                          type VARCHAR(50),
                          distance DECIMAL,
                          siteID INT NOT NULL,
                          FOREIGN KEY(siteID) REFERENCES Toursite_Located(siteID) ON DELETE CASCADE
);

INSERT INTO Amenities VALUES (3001, 'Pan Pacific Vancouver', 'hotel', 0.4, 0001);
INSERT INTO Amenities VALUES (3002, 'Days Inn by Wyndham Vancouver Downtown', 'hotel', 0.55, 0001);
INSERT INTO Amenities VALUES (3003, 'Vancouver Marriott Pinnacle Downtown Hotel', 'hotel', 0.35, 0001);
INSERT INTO Amenities VALUES (3004, 'Millennium Hilton New York One UN Plaza', 'hotel', 0.32, 0002);
INSERT INTO Amenities VALUES (3005, 'Palazzo Veneziano', 'hotel', 0.23, 0003);
INSERT INTO Amenities VALUES (3006, 'Mahonys Tavern Convention Centre', 'restaurant', 0.29, 0001);
INSERT INTO Amenities VALUES (3007, 'Cactus Club Cafe Coal Harbour', 'restaurant', 0.23, 0001);
INSERT INTO Amenities VALUES (3008, 'ARC RESTAURANT', 'restaurant', 1.23, 0002);
INSERT INTO Amenities VALUES (3009, 'Rio Novo','restaurant', 0.88, 0003);
INSERT INTO Amenities VALUES (3010, 'Gotham Restaurant', 'restaurant', 0.55, 0004);



CREATE TABLE Hotel(
                      name VARCHAR(50),
                      AmID INT,
                      rooms INT,
                      PRIMARY KEY(AmID),
                      FOREIGN KEY(AmID) REFERENCES Amenities(AmID) ON DELETE CASCADE
);

INSERT INTO Hotel VALUES ('Pan Pacific Vancouver', 3001, 503);
INSERT INTO Hotel VALUES ('Days Inn by Wyndham Vancouver Downtown', 3002, 85);
INSERT INTO Hotel VALUES ('Vancouver Marriott Pinnacle Downtown Hotel', 3003, 624);
INSERT INTO Hotel VALUES ('Millennium Hilton New York One UN Plaza', 3004, 439);
INSERT INTO Hotel VALUES ('Palazzo Veneziano', 3005, 84);



CREATE TABLE Restaurant(
                           name VARCHAR(50),
                           AmID INT,
                           tables INT,
                           PRIMARY KEY(AmID),
                           FOREIGN KEY(AmID) REFERENCES Amenities(AmID) ON DELETE CASCADE
);

INSERT INTO Restaurant VALUES ('Mahonys Tavern Convention Centre', 3006, 10);
INSERT INTO Restaurant VALUES ('Cactus Club Cafe Coal Harbour', 3007, 23);
INSERT INTO Restaurant VALUES ('ARC RESTAURANT', 3008, 18);
INSERT INTO Restaurant VALUES ('Rio Novo', 3009, 12);
INSERT INTO Restaurant VALUES ('Gotham Restaurant', 3010, 28);



CREATE TABLE Review(
                       RevID INT PRIMARY KEY,
                       cmt VARCHAR(50),
                       InfoID INT NOT NULL,
                       FOREIGN KEY(InfoID) REFERENCES Information_Offers(InfoID) ON DELETE CASCADE
);

INSERT INTO Review VALUES (4001, 'It was very nice', 2001);
INSERT INTO Review VALUES (4002, 'I loved the food', 2001);
INSERT INTO Review VALUES (4003, 'Really fun event! Would come next time', 2001);
INSERT INTO Review VALUES (4004, 'Too crowded', 2001);
INSERT INTO Review VALUES (4005, 'Awesome, highly recommended', 2001);



CREATE TABLE Ratings(
                        score INT,
                        RevID INT,
                        PRIMARY KEY(RevID, score),
                        FOREIGN KEY(RevID) REFERENCES Review(RevID) ON DELETE CASCADE
);

INSERT INTO Ratings VALUES (4, 4001);
INSERT INTO Ratings VALUES (3, 4002);
INSERT INTO Ratings VALUES (5, 4003);
INSERT INTO Ratings VALUES (1, 4004);
INSERT INTO Ratings VALUES (4, 4005);
