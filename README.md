# Interview test project

## Environment

The TruApiProxy api key must be set in the environment variable `API_KEY` 

## Considerations

* If the company search could return a considerable number of companies, we should consider turning the whole API into async as responding requires firing off one HTTP request per company.
* I've decided that for example `CompanyWithOfficers` should not extend `Company` (this would've required converting them to classes) because we don't want our API to automatically reflect changes to the underlying API.
* `CompanyFetchService` could return `Stream<Company>` instead of an array but I've decided to go with the latter for easier readability and simpler test setup. If our measurements show that creating the extra array impacts performance too much (unlikely when we also fire off an HTTP request in the same breath), we could refactor it to use streams throughout.
* When constructing mocks in the `*FetchService` tests, we rely on the exact URI pattern used by the UUT, this may potentially lead to future test fragility.
* As the specification didn't mention what happens with `TruApiProxy` calls when no company/officer is found, for the sake of simplicity I assumed they return an empty response with `"total_results": 0`. In reality I would've sought clarification of this.
