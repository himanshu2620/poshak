package com.iiitb.poshak.logging;

import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoggingRepository extends MongoRepository<Logging, String> {
    Logging findByEmail(String emailId);

    @Aggregation(pipeline = {"{ $match: {email_id: ?2}}",
            "{ $unwind: $log}",
            "{ $match: {'log.date': {$gte: ?0, $lte : ?1}}}",
            "{ $group: {_id: $_id, email_id: {$first : $email_id}, log: {$push: $log}}}"})
    AggregationResults<Logging> aggregateLogsByTime(Long startTime, Long endTime, String email);
}


/*
1. filter
db.logging.aggregate([
    { $match: {email_id: "vijaya@gmail.com"}},
    { $unwind: '$log'},
    { $match: {'log.date': {$gte: 99, $lte : 600}}},
    { $group: {_id: '$_id', email_id: {$first : '$email_id'}, log: {$push: '$log'}}}
]).pretty()

2. insert to a sublist
db.logging.update(
    { "_id": ObjectId("607c738c80b732b7856f4e65")},
    { "$push":
        {"log":
            { "$each":[
                {
                    "calorie" : 22,
                    "protein" : 22,
                    "fat" : 22,
                    "carbs" : 22,
                    "date" : 1619007600000
                },
                {
                    "calorie" : 33,
                    "protein" : 33,
                    "fat" : 33,
                    "carbs" : 33,
                    "date" : 1619007648846
                }
            ]}
        }
    }
)

3. delete from sublist
db.logging.update(
  {_id: ObjectId("607c738c80b732b7856f4e65")},
  {$pull: {'log': <value | condition>
}}
)

 */