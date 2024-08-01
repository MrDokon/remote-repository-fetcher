# Remote Repository Fetcher

Spring WebFlux application fetching data about user repositories and branches from [Github API](https://developer.github.com/v3).

## Getting Started

These instructions will give you a copy of the project up and running on
your local machine for development and testing purposes.

### Prerequisites

Requirements:
- [Java 21](https://www.oracle.com/pl/java/technologies/downloads/)
- [Gradle](https://gradle.org/install/)

### Running the application

```bash
# Clone this repository
$ git clone https://github.com/MrDokon/remote-repository-fetcher.git

# Go into the repository
$ cd remote-repository-fetcher

# Run the app
$ ./gradlew bootRun
```

### Fetching Github Data

Application allows to list all github repositories of given user,  
which are not forks (by default).  

Application endpoint: /repositories/{username}

```bash
# Sample Request
$ curl http://localhost:8080/repositories/MrDokon

# Response
[
    {
	"repository": {
		"name": "cars-app",
		"ownerLogin": "MrDokon"
	},
	"branches": [
		{
			"name": "main",
			"lastCommitSha": "4a68f41e5df26de1c0d9dddecc91036b0c50e328"
		},
		{
			"name": "new-mock-branch",
			"lastCommitSha": "36bbb2990a2fc016fcb877886ce91de27a761a34"
		}
	]
    }
...
]
```
> **Note**
> If you want to see all repos with forks, pass includeForks=true parameter.

### Features

- Reactive approach (it should fetch large amounts of data really fast)
- Caching Enabled
- Service unit tests included (Mockito/ReactorTest)
- Controller Integration tests included (Wiremock/WebTestClient)

### Limitations

Number of GitHub requests is limited.  
See [Rate limits for the REST API](https://docs.github.com/en/rest/using-the-rest-api/rate-limits-for-the-rest-api)

