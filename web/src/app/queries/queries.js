import { Query } from 'avenger/lib/graph';
import { Expire } from 'avenger/lib/cache/strategies';
import t from 'tcomb';
import API from 'API';

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
    exchanges = ['Bitfinex', 'Bitstamp', 'Kraken'],
    base = undefined,
    quote = undefined,
    sortBy = 'Exchange',
    ascending = true
  }) => {
    return API.productsController_read({
      exchanges: JSON.stringify(exchanges), // doing this to prevent wrong serialization
      base,
      quote,
      sortBy,
      ascending
    });
  }
});

