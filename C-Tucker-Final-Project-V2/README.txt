This project uses SQL Server LocalDB with Entity Framework Core.
Before running the application, you must create the database by applying migrations.
Run the following command in the project directory:

dotnet ef database update

## Connection String

The application uses the default LocalDB connection:

Server=(localdb)\MSSQLLocalDB;
Database=TriviaDb;
Trusted_Connection=True;
MultipleActiveResultSets=true

No changes are required if LocalDB is installed.

## Leaderboard Requirement

The leaderboard relies on the database table "Scores".

If the database is not created using migrations, the leaderboard will not work and will throw an error.

Running "dotnet ef database update" ensures the Scores table is created.