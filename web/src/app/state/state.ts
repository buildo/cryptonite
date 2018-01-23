import init from 'buildo-state/lib';
import * as t from 'tcomb';
import { Currency, Column, Exchange } from 'model';

export interface AppState {
  view: string,
  exchanges: Array<Exchange>,
  base?: Currency | undefined,
  quote?: Currency | undefined,
  sortBy?: Column,
  ascending?: boolean
};
export const AppState = t.interface<AppState>({
  view: t.String,
  exchanges: t.Any,
  base: t.Any,
  quote: t.Any,
  sortBy: t.Any,
  ascending: t.maybe(t.Boolean)
}, { strict: true });

export const { run, connect, appState } = init<AppState>(AppState);
import { TransitionFunction } from 'buildo-state/lib/transition';
export type TransitionFunction = TransitionFunction<AppState>;
