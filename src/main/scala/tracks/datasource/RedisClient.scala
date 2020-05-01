package tracks.datasource

/*
Available Redis Queries

1. HDEL key [field...] -> Delete one or more hash fields
2. HEXISTS key field -> Determine is a hash field exists
3. HGET key field -> Get the value of a hash field
4. HGETALL key -> Get all the fields and values in a hash
5. HINCRBY key field increment -> Increment the integer value of a hash field by the given number
6. HINCRBYFLOAT key field increment -> Increment the float value of a hash field by the given amount
7. HKEYS key -> Get all the fields in a hash
8. HLEN key -> Get the number of fields in a hash
9. HMGET key field [field...] -> Get the values of all the given hash fields
10. HMSET key value field [field value...] -> Set multiple hash fields to multiple values
11. HSET key field value [field value...] -> Set the string value of a hash field
12. HSETNX key field value -> Set the value of a hash field, only if the field does not exist
13. HSTRLEN key field -> Get the length of the value of a hash field
14. HVALS key -> Get all the values in a hash
15. HSCAN key cursor [MATCH pattern...] -> Incrementally iterate hash fields and associated values
 */

class RedisClient {

}