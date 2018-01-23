import { Query } from 'avenger/lib/graph';
import { Expire } from 'avenger/lib/cache/strategies';
import t from 'tcomb';
import API from 'API';

const defaultExchanges = ['Bitfinex', 'Bitstamp', 'Kraken'];

const checkExchanges =
  (exchanges) =>
  (exchanges.length === 1 && exchanges[0] === '') ?
  JSON.stringify(defaultExchanges) : JSON.stringify(exchanges);

export const getProducts = Query({
  id: 'getProducts',
  params: {
    exchanges: t.Any,
    base: t.Any,
    quote: t.Any,
    sortBy: t.Any,
    ascending: t.maybe(t.Boolean)
  },
  cacheStrategy: new Expire(15000),
  returnType: t.Any,
  fetch: ({
    exchanges = defaultExchanges,
    base = undefined,
    quote = undefined,
    sortBy = 'Exchange',
    ascending = true
  }) => {
    return API.productsController_read({
      exchanges: checkExchanges(exchanges), // doing this to prevent wrong serialization
      base,
      quote,
      sortBy,
      ascending
    });
  }
});
