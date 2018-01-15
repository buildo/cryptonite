import container from 'container';
import Products from './Products';
import { Book } from '../../metarpheus/model-ts';
import { TransitionFunction } from 'state';

type MapProps = {
  transition: TransitionFunction,
  getProducts: Array<Book>
};

export default container(Products)({
  queries: ['getProducts'],
  isReady: () => true,
  mapProps: ({ getProducts = []} : MapProps) => ({
    books: getProducts
  })
}) as any as React.ComponentType;
