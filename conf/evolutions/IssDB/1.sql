-- Stock schema

-- !Ups

create table `stocks` (
    `id` INT NOT NULL PRIMARY KEY,
    `secId` TEXT NOT NULL,
    `shortName` TEXT NOT NULL,
    `regNumber` TEXT,
    `name` TEXT NOT NULL,
    `isin` TEXT,
    `isTraded` TEXT,
    `emitentId` TEXT,
    `emitentTitle` TEXT,
    `emitentInn` TEXT,
    `emitentOkpo` TEXT,
    `gosReg` TEXT,
    `stockType` TEXT NOT NULL,
    `group` TEXT NOT NULL,
    `primaryBoardId` TEXT NOT NULL,
    `marketPriceBoardId` TEXT
)

-- !Downs
drop table `stocks`