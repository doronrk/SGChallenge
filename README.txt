Space and time complexity of each endpoint
    - `PUT` `/names/[name]`
        - time is O(n), where n is the length of the name. Although the endpoint
            simply inserts the pair into a hashmap, computing the hash of a string 
            of length n is O(n).
        - space is O(n) - where n is the length of the name & the url. The endpoint induces
            no memory overhead beyond the data payload. The size of the hashmap
            is O(p) where p is the number of name/url pairs.
    - `GET` `/names/[name]`
        - time is O(n) for the same reasons as PUT
        - space is O(n) for the same reasons as PUT
    - `DELETE`
        - time is O(1) since the hashmap is re-initialized. 
    - `POST`
        - time is O(n) where n is the length of the HTML snippet.
            - java regex is O(n), parsing the HTML DOM is also O(n), a 
                StringBuilder is used instead of a Java String so that appending
                the output HTML is also O(n)
            - checking for URLs in the hashmap is O(1) and happens at most 
            n times
        - space is O(n)
            - The endpoint induces no memory overhead beyond the data payload.

Build Instructions
    - If you have Maven and Java 8 you will be good to go.
    - Open the project, let Maven grab all of the dependencies, and run the
    main method in LinkService.java
    - The port 3001, as in the test script