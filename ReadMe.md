# Treat Tinder

This microservice manages dogs and customer data. Also manages customer like /disliked dogs

The following are the APIs exposed :
# Dog APIs
#### 1.  GET  `tret/petfinder`

API will fetch the dogs data from PetFinder. 

a. Get the last processed page from database. Increment the page number \
b. Call the API \
c. Process the response and store the data.

#### 2.  POST  `treat/tinder` 
Filter the data from database and returns the list of the dogs that matches the criteria.
Sample Request Body \
```
{
  "organizationID": "TN907",
  "gender": "Male",
  "breed": {
    "primary": "",
    "secondary": "",
    "mixed": false,
    "unknown": false
  },
  "address": {
    "address1": "",
    "address2": "",
    "city": "",
    "state": "",
    "postcode": "",
    "country": ""
  }
}

```

Sample Response : 
```
[
    {
        "id": 7,
        "dogID": 70775320,
        "name": "Hoss",
        "url": "https://www.petfinder.com/dog/hoss-70775320/tn/hartsville/hartsville-trousdale-county-animal-shelter-tn907/?referrer_id=865644a2-78d8-462b-b3e6-dc1ec6e37f24&utm_source=api&utm_medium=partnership&utm_content=865644a2-78d8-462b-b3e6-dc1ec6e37f24",
        "age": "Adult",
        "gender": "Male",
        "size": "Large",
        "orgID": "TN907"
    },
    {
        "id": 8,
        "dogID": 70775321,
        "name": "Brutus",
        "url": "https://www.petfinder.com/dog/brutus-70775321/tn/hartsville/hartsville-trousdale-county-animal-shelter-tn907/?referrer_id=865644a2-78d8-462b-b3e6-dc1ec6e37f24&utm_source=api&utm_medium=partnership&utm_content=865644a2-78d8-462b-b3e6-dc1ec6e37f24",
        "age": "Adult",
        "gender": "Male",
        "size": "Large",
        "orgID": "TN907"
    },
    {
        "id": 11,
        "dogID": 70775325,
        "name": "Todd",
        "url": "https://www.petfinder.com/dog/todd-70775325/tn/hartsville/hartsville-trousdale-county-animal-shelter-tn907/?referrer_id=865644a2-78d8-462b-b3e6-dc1ec6e37f24&utm_source=api&utm_medium=partnership&utm_content=865644a2-78d8-462b-b3e6-dc1ec6e37f24",
        "age": "Young",
        "gender": "Male",
        "size": "Medium",
        "orgID": "TN907"
    },
    {
        "id": 12,
        "dogID": 70775326,
        "name": "Copper",
        "url": "https://www.petfinder.com/dog/copper-70775326/tn/hartsville/hartsville-trousdale-county-animal-shelter-tn907/?referrer_id=865644a2-78d8-462b-b3e6-dc1ec6e37f24&utm_source=api&utm_medium=partnership&utm_content=865644a2-78d8-462b-b3e6-dc1ec6e37f24",
        "age": "Young",
        "gender": "Male",
        "size": "Medium",
        "orgID": "TN907"
    },
    {
        "id": 13,
        "dogID": 70775327,
        "name": "Doc Holliday",
        "url": "https://www.petfinder.com/dog/doc-holliday-70775327/tn/hartsville/hartsville-trousdale-county-animal-shelter-tn907/?referrer_id=865644a2-78d8-462b-b3e6-dc1ec6e37f24&utm_source=api&utm_medium=partnership&utm_content=865644a2-78d8-462b-b3e6-dc1ec6e37f24",
        "age": "Young",
        "gender": "Male",
        "size": "Medium",
        "orgID": "TN907"
    }
]
```


#### 4. POST `treat\tinder\customer\{cusotmerID}\dogs\{dogID}\reaction`
Saves dog liked by customer. 
Request Body:
```
{
"interactionType": "LIKE"
}
``` 
Sample Response 
```
{
    "success": true,
    "message": "Successfully saved the customer interaction."
}
```



## Steps to test the service 
After the service is running successfully , follow the below steps.
1. Run  GET  `tret/petfinder` API to fetch all the data and store in the db. 
2. Create a customer with the help of  POST `treat/tinder/customer`
3. Fetch the dogs by providing the filter.
4. Call  POST `treat\tinder\customer\{cusotmerID}\dogs\{dogID}\reaction` 