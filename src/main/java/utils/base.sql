/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Nathalie Andrandrain
 * Created: 2 mai 2018
 */

create database hopital;
use hopital;

create table chambre(
id SERIAL primary key,
numero varchar(50),
disponible float
);

create table mouvement(
id SERIAL primary key,
patient int,
service int,
chambre int,
dateEntre date,
dateSortie date,
foreign key (patient) references patient(id),
foreign key (service) references service(id),
foreign key (chambre) references chambre(id)
);

