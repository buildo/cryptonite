import getRoutes from 'metarpheus/api';

export default getRoutes({
  apiEndpoint: 'http://localhost:1337/localhost:9090',
  timeout: 30000,
  unwrapApiResponse: (resp: any) => resp
});
