import { Query } from 'avenger/lib/graph';
import { Expire } from 'avenger/lib/cache/strategies';
import t from 'tcomb';
import API from 'API';

export const getProducts = Query({
  id: 'getProducts',
  cacheStrategy: new Expire(15000),
  returnType: t.Any,
  fetch: API.productsController_read
});

