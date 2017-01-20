USE eppione;

-- ========== DB SETUP script ==========

INSERT INTO `role` VALUES 
('ROLE_ADMIN'), 
('ROLE_CONTRIBUTOR'), 
('ROLE_MANAGER'), 
('ROLE_CITITOR');

INSERT INTO `department`
(`id`, `name`)
VALUES
    (1, 'UBB'), (2, 'Directia Financiar-Contabila');

INSERT INTO `user`
(`id`,
 `first_name`,
 `last_name`,
 `username`,
 `email`,
 `password`,
 `function`,
 `id_superior`,
 `department_id`)
VALUES
    (1, 'Diana', 'Dobra', 'dianadobra', 'dianadobra95@yahoo.ro', '$2a$10$Q2AOEO4LHdoX6cbNtQw00ux8pOXeVWuvaJwvyRu/vb55wYnilkj1O', 10, 10, 1),
    (2, 'Sandi', 'Petrut', 'sandipetrut', 'sandi@mail.com', '$2a$10$Q2AOEO4LHdoX6cbNtQw00ux8pOXeVWuvaJwvyRu/vb55wYnilkj1O', 10, 10, 1),
    (3, 'Helga', 'Fekete', 'helgafekete', 'helga@mail.com', '$2a$10$Q2AOEO4LHdoX6cbNtQw00ux8pOXeVWuvaJwvyRu/vb55wYnilkj1O', 10, 10, 1),
    (4, 'Mircea', 'Ciuciu', 'mirceaciuciu', 'mircea@mail.com', '$2a$10$Q2AOEO4LHdoX6cbNtQw00ux8pOXeVWuvaJwvyRu/vb55wYnilkj1O', 10, 10, 1),
    (5, 'Leo', 'Corocea', 'leocorocea', 'leo@mail.com', '$2a$10$Q2AOEO4LHdoX6cbNtQw00ux8pOXeVWuvaJwvyRu/vb55wYnilkj1O', 10, 10, 1),
    (6, 'Vsa', 'Draghita', 'vsadraghita', 'vsa@mail.com', '$2a$10$Q2AOEO4LHdoX6cbNtQw00ux8pOXeVWuvaJwvyRu/vb55wYnilkj1O', 10, 10, 1),
    (7, 'Felix', 'Danut', 'felixdanut', 'felix@mail.com', '$2a$10$Q2AOEO4LHdoX6cbNtQw00ux8pOXeVWuvaJwvyRu/vb55wYnilkj1O', 10, 10, 1),
    (8, 'Daniel', 'Bogdan', 'danielbogdan', 'daniel@mail.com', '$2a$10$Q2AOEO4LHdoX6cbNtQw00ux8pOXeVWuvaJwvyRu/vb55wYnilkj1O', 10, 10, 1),
    (9, 'John', 'Smith', 'johnsmith', 'johnsmith', '$2a$10$Q2AOEO4LHdoX6cbNtQw00ux8pOXeVWuvaJwvyRu/vb55wYnilkj1O', 0, 9, 1),
    (10, 'Jane', 'Smith', 'janesmith', 'janesmith@mail.com', '$2a$10$Q2AOEO4LHdoX6cbNtQw00ux8pOXeVWuvaJwvyRu/vb55wYnilkj1O', 1, 11, 1),
    (11, 'Aria', 'Hastings', 'ariahastings', 'ariahastings@mail.com', '$2a$10$Q2AOEO4LHdoX6cbNtQw00ux8pOXeVWuvaJwvyRu/vb55wYnilkj1O', 7, 11, 2),
    (12, 'Irene', 'Fields', 'irenefields', 'irenefields@mail.com', '$2a$10$Q2AOEO4LHdoX6cbNtQw00ux8pOXeVWuvaJwvyRu/vb55wYnilkj1O', 2, 12, 1),
    (13, 'Emily', 'Specter', 'emilyspecter', 'emilyspecter@mail.com', '$2a$10$Q2AOEO4LHdoX6cbNtQw00ux8pOXeVWuvaJwvyRu/vb55wYnilkj1O', 3, 13, 1),
    (14, 'Mike', 'Grey', 'mikegrey', 'mikegrey@mail.com', '$2a$10$Q2AOEO4LHdoX6cbNtQw00ux8pOXeVWuvaJwvyRu/vb55wYnilkj1O', 4, 11, 1),
    (15, 'Anna', 'Steele', 'annasteele', 'annasteele@mail.com', '$2a$10$Q2AOEO4LHdoX6cbNtQw00ux8pOXeVWuvaJwvyRu/vb55wYnilkj1O', 5, 16, 1),
    (16, 'Johanna', 'Adams', 'johannaadams', 'johannaadams@mail.com', '$2a$10$Q2AOEO4LHdoX6cbNtQw00ux8pOXeVWuvaJwvyRu/vb55wYnilkj1O', 8, 11, 1),
    (17, 'Adele', 'Wiliams', 'adelewiliams', 'adelewiliams@mail.com', '$2a$10$Q2AOEO4LHdoX6cbNtQw00ux8pOXeVWuvaJwvyRu/vb55wYnilkj1O', 6, 16, 1);
    

INSERT INTO `user_role`
(`user_id`, 
 `role_name`) 
VALUES 
(1, 'ROLE_ADMIN'),
(2, 'ROLE_ADMIN'),
(3, 'ROLE_CONTRIBUTOR'),
(4, 'ROLE_CITITOR'),
(5, 'ROLE_MANAGER'),
(6, 'ROLE_ADMIN'),
(7, 'ROLE_CONTRIBUTOR'),
(8, 'ROLE_MANAGER'),
(9, 'ROLE_ADMIN'),
(10, 'ROLE_ADMIN'),
(11, 'ROLE_CONTRIBUTOR'),
(12, 'ROLE_MANAGER'),
(13, 'ROLE_MANAGER'),
(14, 'ROLE_ADMIN'),
(15, 'ROLE_CONTRIBUTOR'),
(16, 'ROLE_MANAGER'),
(17, 'ROLE_MANAGER');

INSERT INTO `funding`
(`id`,
 `type`)
VALUES
(1, 'Fara finantare'),
(2, 'Finantare din bugetul facultatii'),
(3, 'Finantare din bugetul universitatii'),
(4, 'Finantare din proiecte sau granturi'),
(5, 'Finantare combinata (facultate si grant)');


CREATE TABLE IF NOT EXISTS `country` (
    `id` int(5) NOT NULL AUTO_INCREMENT,
    `country_code` char(2) NOT NULL DEFAULT '',
    `country_name` varchar(45) NOT NULL DEFAULT '',
    `currency` char(3) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=0;

INSERT INTO `country` 
(`country_code`,
 `country_name`, 
 `currency`) 
VALUES
('AD', 'Andorra', 'EUR'),
('AE', 'United Arab Emirates', 'AED'),
('AF', 'Afghanistan', 'AFN'),
('AG', 'Antigua and Barbuda', 'XCD'),
('AI', 'Anguilla', 'XCD'),
('AL', 'Albania', 'ALL'),
('AM', 'Armenia', 'AMD'),
('AO', 'Angola', 'AOA'),
('AQ', 'Antarctica', ''),
('AR', 'Argentina', 'ARS'),
('AS', 'American Samoa', 'USD'),
('AT', 'Austria', 'EUR'),
('AU', 'Australia', 'AUD'),
('AW', 'Aruba', 'AWG'),
('AX', 'Åland', 'EUR'),
('AZ', 'Azerbaijan', 'AZN'),
('BA', 'Bosnia and Herzegovina', 'BAM'),
('BB', 'Barbados', 'BBD'),
('BD', 'Bangladesh', 'BDT'),
('BE', 'Belgium', 'EUR'),
('BF', 'Burkina Faso', 'XOF'),
('BG', 'Bulgaria', 'BGN'),
('BH', 'Bahrain', 'BHD'),
('BI', 'Burundi', 'BIF'),
('BJ', 'Benin', 'XOF'),
('BL', 'Saint Barthélemy', 'EUR'),
('BM', 'Bermuda', 'BMD'),
('BN', 'Brunei', 'BND'),
('BO', 'Bolivia', 'BOB'),
('BQ', 'Bonaire', 'USD'),
('BR', 'Brazil', 'BRL'),
('BS', 'Bahamas', 'BSD'),
('BT', 'Bhutan', 'BTN'),
('BV', 'Bouvet Island', 'NOK'),
('BW', 'Botswana', 'BWP'),
('BY', 'Belarus', 'BYR'),
('BZ', 'Belize', 'BZD'),
('CA', 'Canada', 'CAD'),
('CC', 'Cocos [Keeling] Islands', 'AUD'),
('CD', 'Democratic Republic of the Congo', 'CDF'),
('CF', 'Central African Republic', 'XAF'),
('CG', 'Republic of the Congo', 'XAF'),
('CH', 'Switzerland', 'CHF'),
('CI', 'Ivory Coast', 'XOF'),
('CK', 'Cook Islands', 'NZD'),
('CL', 'Chile', 'CLP'),
('CM', 'Cameroon', 'XAF'),
('CN', 'China', 'CNY'),
('CO', 'Colombia', 'COP'),
('CR', 'Costa Rica', 'CRC'),
('CU', 'Cuba', 'CUP'),
('CV', 'Cape Verde', 'CVE'),
('CW', 'Curacao', 'ANG'),
('CX', 'Christmas Island', 'AUD'),
('CY', 'Cyprus', 'EUR'),
('CZ', 'Czechia', 'CZK'),
('DE', 'Germany', 'EUR'),
('DJ', 'Djibouti', 'DJF'),
('DK', 'Denmark', 'DKK'),
('DM', 'Dominica', 'XCD'),
('DO', 'Dominican Republic', 'DOP'),
('DZ', 'Algeria', 'DZD'),
('EC', 'Ecuador', 'USD'),
('EE', 'Estonia', 'EUR'),
('EG', 'Egypt', 'EGP'),
('EH', 'Western Sahara', 'MAD'),
('ER', 'Eritrea', 'ERN'),
('ES', 'Spain', 'EUR'),
('ET', 'Ethiopia', 'ETB'),
('FI', 'Finland', 'EUR'),
('FJ', 'Fiji', 'FJD'),
('FK', 'Falkland Islands', 'FKP'),
('FM', 'Micronesia', 'USD'),
('FO', 'Faroe Islands', 'DKK'),
('FR', 'France', 'EUR'),
('GA', 'Gabon', 'XAF'),
('GB', 'United Kingdom', 'GBP'),
('GD', 'Grenada', 'XCD'),
('GE', 'Georgia', 'GEL'),
('GF', 'French Guiana', 'EUR'),
('GG', 'Guernsey', 'GBP'),
('GH', 'Ghana', 'GHS'),
('GI', 'Gibraltar', 'GIP'),
('GL', 'Greenland', 'DKK'),
('GM', 'Gambia', 'GMD'),
('GN', 'Guinea', 'GNF'),
('GP', 'Guadeloupe', 'EUR'),
('GQ', 'Equatorial Guinea', 'XAF'),
('GR', 'Greece', 'EUR'),
('GS', 'South Georgia and the South Sandwich Islands', 'GBP'),
('GT', 'Guatemala', 'GTQ'),
('GU', 'Guam', 'USD'),
('GW', 'Guinea-Bissau', 'XOF'),
('GY', 'Guyana', 'GYD'),
('HK', 'Hong Kong', 'HKD'),
('HM', 'Heard Island and McDonald Islands', 'AUD'),
('HN', 'Honduras', 'HNL'),
('HR', 'Croatia', 'HRK'),
('HT', 'Haiti', 'HTG'),
('HU', 'Hungary', 'HUF'),
('ID', 'Indonesia', 'IDR'),
('IE', 'Ireland', 'EUR'),
('IL', 'Israel', 'ILS'),
('IM', 'Isle of Man', 'GBP'),
('IN', 'India', 'INR'),
('IO', 'British Indian Ocean Territory', 'USD'),
('IQ', 'Iraq', 'IQD'),
('IR', 'Iran', 'IRR'),
('IS', 'Iceland', 'ISK'),
('IT', 'Italy', 'EUR'),
('JE', 'Jersey', 'GBP'),
('JM', 'Jamaica', 'JMD'),
('JO', 'Jordan', 'JOD'),
('JP', 'Japan', 'JPY'),
('KE', 'Kenya', 'KES'),
('KG', 'Kyrgyzstan', 'KGS'),
('KH', 'Cambodia', 'KHR'),
('KI', 'Kiribati', 'AUD'),
('KM', 'Comoros', 'KMF'),
('KN', 'Saint Kitts and Nevis', 'XCD'),
('KP', 'North Korea', 'KPW'),
('KR', 'South Korea', 'KRW'),
('KW', 'Kuwait', 'KWD'),
('KY', 'Cayman Islands', 'KYD'),
('KZ', 'Kazakhstan', 'KZT'),
('LA', 'Laos', 'LAK'),
('LB', 'Lebanon', 'LBP'),
('LC', 'Saint Lucia', 'XCD'),
('LI', 'Liechtenstein', 'CHF'),
('LK', 'Sri Lanka', 'LKR'),
('LR', 'Liberia', 'LRD'),
('LS', 'Lesotho', 'LSL'),
('LT', 'Lithuania', 'EUR'),
('LU', 'Luxembourg', 'EUR'),
('LV', 'Latvia', 'EUR'),
('LY', 'Libya', 'LYD'),
('MA', 'Morocco', 'MAD'),
('MC', 'Monaco', 'EUR'),
('MD', 'Moldova', 'MDL'),
('ME', 'Montenegro', 'EUR'),
('MF', 'Saint Martin', 'EUR'),
('MG', 'Madagascar', 'MGA'),
('MH', 'Marshall Islands', 'USD'),
('MK', 'Macedonia', 'MKD'),
('ML', 'Mali', 'XOF'),
('MM', 'Myanmar [Burma]', 'MMK'),
('MN', 'Mongolia', 'MNT'),
('MO', 'Macao', 'MOP'),
('MP', 'Northern Mariana Islands', 'USD'),
('MQ', 'Martinique', 'EUR'),
('MR', 'Mauritania', 'MRO'),
('MS', 'Montserrat', 'XCD'),
('MT', 'Malta', 'EUR'),
('MU', 'Mauritius', 'MUR'),
('MV', 'Maldives', 'MVR'),
('MW', 'Malawi', 'MWK'),
('MX', 'Mexico', 'MXN'),
('MY', 'Malaysia', 'MYR'),
('MZ', 'Mozambique', 'MZN'),
('NA', 'Namibia', 'NAD'),
('NC', 'New Caledonia', 'XPF'),
('NE', 'Niger', 'XOF'),
('NF', 'Norfolk Island', 'AUD'),
('NG', 'Nigeria', 'NGN'),
('NI', 'Nicaragua', 'NIO'),
('NL', 'Netherlands', 'EUR'),
('NO', 'Norway', 'NOK'),
('NP', 'Nepal', 'NPR'),
('NR', 'Nauru', 'AUD'),
('NU', 'Niue', 'NZD'),
('NZ', 'New Zealand', 'NZD'),
('OM', 'Oman', 'OMR'),
('PA', 'Panama', 'PAB'),
('PE', 'Peru', 'PEN'),
('PF', 'French Polynesia', 'XPF'),
('PG', 'Papua New Guinea', 'PGK'),
('PH', 'Philippines', 'PHP'),
('PK', 'Pakistan', 'PKR'),
('PL', 'Poland', 'PLN'),
('PM', 'Saint Pierre and Miquelon', 'EUR'),
('PN', 'Pitcairn Islands', 'NZD'),
('PR', 'Puerto Rico', 'USD'),
('PS', 'Palestine', 'ILS'),
('PT', 'Portugal', 'EUR'),
('PW', 'Palau', 'USD'),
('PY', 'Paraguay', 'PYG'),
('QA', 'Qatar', 'QAR'),
('RE', 'Réunion', 'EUR'),
('RO', 'Romania', 'RON'),
('RS', 'Serbia', 'RSD'),
('RU', 'Russia', 'RUB'),
('RW', 'Rwanda', 'RWF'),
('SA', 'Saudi Arabia', 'SAR'),
('SB', 'Solomon Islands', 'SBD'),
('SC', 'Seychelles', 'SCR'),
('SD', 'Sudan', 'SDG'),
('SE', 'Sweden', 'SEK'),
('SG', 'Singapore', 'SGD'),
('SH', 'Saint Helena', 'SHP'),
('SI', 'Slovenia', 'EUR'),
('SJ', 'Svalbard and Jan Mayen', 'NOK'),
('SK', 'Slovakia', 'EUR'),
('SL', 'Sierra Leone', 'SLL'),
('SM', 'San Marino', 'EUR'),
('SN', 'Senegal', 'XOF'),
('SO', 'Somalia', 'SOS'),
('SR', 'Suriname', 'SRD'),
('SS', 'South Sudan', 'SSP'),
('ST', 'São Tomé and Príncipe', 'STD'),
('SV', 'El Salvador', 'USD'),
('SX', 'Sint Maarten', 'ANG'),
('SY', 'Syria', 'SYP'),
('SZ', 'Swaziland', 'SZL'),
('TC', 'Turks and Caicos Islands', 'USD'),
('TD', 'Chad', 'XAF'),
('TF', 'French Southern Territories', 'EUR'),
('TG', 'Togo', 'XOF'),
('TH', 'Thailand', 'THB'),
('TJ', 'Tajikistan', 'TJS'),
('TK', 'Tokelau', 'NZD'),
('TL', 'East Timor', 'USD'),
('TM', 'Turkmenistan', 'TMT'),
('TN', 'Tunisia', 'TND'),
('TO', 'Tonga', 'TOP'),
('TR', 'Turkey', 'TRY'),
('TT', 'Trinidad and Tobago', 'TTD'),
('TV', 'Tuvalu', 'AUD'),
('TW', 'Taiwan', 'TWD'),
('TZ', 'Tanzania', 'TZS'),
('UA', 'Ukraine', 'UAH'),
('UG', 'Uganda', 'UGX'),
('UM', 'U.S. Minor Outlying Islands', 'USD'),
('US', 'United States', 'USD'),
('UY', 'Uruguay', 'UYU'),
('UZ', 'Uzbekistan', 'UZS'),
('VA', 'Vatican City', 'EUR'),
('VC', 'Saint Vincent and the Grenadines', 'XCD'),
('VE', 'Venezuela', 'VEF'),
('VG', 'British Virgin Islands', 'USD'),
('VI', 'U.S. Virgin Islands', 'USD'),
('VN', 'Vietnam', 'VND'),
('VU', 'Vanuatu', 'VUV'),
('WF', 'Wallis and Futuna', 'XPF'),
('WS', 'Samoa', 'WST'),
('XK', 'Kosovo', 'EUR'),
('YE', 'Yemen', 'YER'),
('YT', 'Mayotte', 'EUR'),
('ZA', 'South Africa', 'ZAR'),
('ZM', 'Zambia', 'ZMW'),
('ZW', 'Zimbabwe', 'ZWL')