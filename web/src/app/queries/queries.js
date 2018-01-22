import { Query } from 'avenger/lib/graph';
import { Expire } from 'avenger/lib/cache/strategies';
import t from 'tcomb';
import API from 'API';

export const getProducts = Query({
  id: 'getProducts',
  params: { sortBy: t.Any, ascending: t.maybe(t.Boolean) },
  cacheStrategy: new Expire(15000),
  returnType: t.Any,
  fetch: ({ sortBy = 'Exchange', ascending = true }) =>
    API.productsController_read({ sortBy, ascending })
});

