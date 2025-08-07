## CashAppStocks

I used Jetpack Compose to build the app. When the app is first run, the MainActivity is launched, which in turn launches CashAppStocksNavigation.
CashAppStocksNavigation is a composable function that defines navigation for the App. 
I have created 3 pages (screens) for the app, the Main screen, the Details screen and the About screen.

- StocksListScreen is the starting destination or main screen, used to show the list of stocks loaded from the provided URL. 
When this screen is loaded, the stocks are not fetched yet. A "Load Stocks" button is provided and clicking it loads stocks from the url.
If the network request returns a list of stocks, it is shown in a column. 
If the network request returns an empty list, a message with a button is shown to try to reload again.
If the network request errors out due to some reason like malformed result or offline issues, a message with a try again button is shown.

- DetailsScreen is the screen that is designed to show the details of the clicked stock on the list. Currently it shows name and price only. 
Conceptually, this page should show more details of the stock, but I'm passing the values as navigation arguments right now, which is not ideal.
Usually we should be passing just the id to the next page and fetch more details of the stock again from the network. 

- AboutScreen only shows a message right now and is shown when we click About button on the top right of the app bar. 
This page was added to demonstrate compose navigation.

I used OkHttp and Retrofit to fetch stocks data from the network. I added Hilt and Dagger for dependency injection later on so that I can unit test the view model easily.
Stock is the data model class for the stock and StocksViewState, a sealed class is used for collecting different results from network requests in StocksApi.


## Future Enhancements
- Make UI better. The stocks list in the main screen looks a bit messy. It can be improved by adding some custom background, different text styles to highlight different information etc.
  The details page is also very basic.
- Create separate pages for list, loading error etc.
- Maybe add some type of data storage for persistence and use it in details page rather than passing the values in navigation arguments.
  
