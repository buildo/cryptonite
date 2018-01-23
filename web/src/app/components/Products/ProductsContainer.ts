import container from 'container';
import Products from './Products';
import { Book, Column, Currency, Exchange } from 'model';
import { TransitionFunction } from 'state';

type MapProps = {
  transition: TransitionFunction,
  getProducts: Array<Book>,
  exchanges: Array<Exchange>,
  base: Currency | undefined,
  quote: Currency | undefined,
  sortBy: Column,
  ascending: boolean
};

export default container(Products)({
  queries: ['getProducts'],
  connect: ['exchanges', 'base', 'quote', 'sortBy', 'ascending'],
  mapProps: ({ transition, getProducts = [], exchanges = [], base, quote, sortBy, ascending } : MapProps) => ({
    books: getProducts,
    exchanges,
    base,
    quote,
    sortBy,
    sortDir: ascending ? 'asc' : 'desc',
    onExchangeChange: ( exchanges ) => {
      console.log(exchanges);
      transition({
        exchanges
      })
    },
    onBaseChange: ( base ) => {
      transition({
        base
      })
    },
    onQuoteChange: ( quote ) => {
      transition({
        quote
      })
    },
    onSortChange: ({ sortBy, sortDir }) => {
      transition({
        sortBy, ascending: sortDir === 'asc'
      })
    }
  })
}) as any as React.ComponentType;
