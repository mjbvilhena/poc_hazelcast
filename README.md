# poc_module_hazelcast
Poc Java API using Spring Framework 4 and Hazelcast


Main Modules:

1. Storage:

    Implementation of a Hazelcast Hazelcast In-Memory Data Grid (IMDG) and Topic. Gets its data from a PostgreSQL database

2. Client_Query:
    
    Implementation of a REST Microservice for read-only operations, against the IMDG. Partly implements the CQRS pattern.

3. Client_Command:

    Implementation of a REST Microservice for write-only operations. Completes the implementation of the CQRS pattern. 
    
    Also partly implements Event Sourcing by deferring all changes to the underlying database to another module. 
    
    This is done using assychronous messages and Hazelcast Topics

4. Client_Command_Topic:
    
    Implementation of the Hazelcast Topic Listener. Persists all changes from Client_Command to the underlying database.

5. Intern:

    Leverages database-level bespoke Change Data Capture (CDC) to keep in synch the Hazelcast IMDG.
  
To summarize: 

    All reads are done direclty against the IMDG. 
    All writes are done directly on the database, through the use of Topics, without interacting with the IMDG.
    All writes to the IMDG are done through the CDC module.

How to load test data:

    Run the StorageApplicationTests.

How to run:

    Run all modules as how they are ordered above (1 to 5). 
    To run Hazelcast in Cluster Mode, just run multiple instances of the Storage module (but overrriding the server.port configuration for each instance).

