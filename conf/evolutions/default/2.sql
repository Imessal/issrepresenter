-- Histories schema

-- !Ups

create table `histories` (
                             `boardId` TEXT NOT NULL ,
                             `tradeDate` VARCHAR(40) NOT NULL,
                             `shortName` TEXT NOT NULL,
                             `secId` VARCHAR(40) NOT NULL,
                             `numTrades` DOUBLE NOT NULL,
                             `value` DOUBLE NOT NULL,
                             `open` DOUBLE,
                             `low` DOUBLE,
                             `high` DOUBLE,
                             `legalClosePrice` DOUBLE,
                             `waPrice` DOUBLE,
                             `close` DOUBLE,
                             `volume` DOUBLE NOT NULL,
                             `marketPrice2` DOUBLE,
                             `marketPrice3` DOUBLE,
                             `admittedQuote` DOUBLE,
                             `mp2valTrd` DOUBLE NOT NULL,
                             `marketPrice3TradesValue` DOUBLE NOT NULL,
                             `admittedValue` DOUBLE NOT NULL,
                             `waVal` DOUBLE,
                             PRIMARY KEY (`tradeDate`, `secId`, `value`)
)

-- !Downs
DROP TABLE `histories`