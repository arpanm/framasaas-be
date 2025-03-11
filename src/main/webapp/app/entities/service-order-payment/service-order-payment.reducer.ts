import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending } from '@reduxjs/toolkit';
import { loadMoreDataWhenScrolled, parseHeaderForLinks } from 'react-jhipster';
import { cleanEntity } from 'app/shared/util/entity-utils';
import { EntityState, IQueryParams, createEntitySlice, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { IServiceOrderPayment, defaultValue } from 'app/shared/model/service-order-payment.model';

const initialState: EntityState<IServiceOrderPayment> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

const apiUrl = 'api/service-order-payments';

// Actions

export const getEntities = createAsyncThunk(
  'serviceOrderPayment/fetch_entity_list',
  async ({ page, size, sort }: IQueryParams) => {
    const requestUrl = `${apiUrl}?${sort ? `page=${page}&size=${size}&sort=${sort}&` : ''}cacheBuster=${new Date().getTime()}`;
    return axios.get<IServiceOrderPayment[]>(requestUrl);
  },
  { serializeError: serializeAxiosError },
);

export const getEntity = createAsyncThunk(
  'serviceOrderPayment/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<IServiceOrderPayment>(requestUrl);
  },
  { serializeError: serializeAxiosError },
);

export const createEntity = createAsyncThunk(
  'serviceOrderPayment/create_entity',
  async (entity: IServiceOrderPayment, thunkAPI) => {
    return axios.post<IServiceOrderPayment>(apiUrl, cleanEntity(entity));
  },
  { serializeError: serializeAxiosError },
);

export const updateEntity = createAsyncThunk(
  'serviceOrderPayment/update_entity',
  async (entity: IServiceOrderPayment, thunkAPI) => {
    return axios.put<IServiceOrderPayment>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
  },
  { serializeError: serializeAxiosError },
);

export const partialUpdateEntity = createAsyncThunk(
  'serviceOrderPayment/partial_update_entity',
  async (entity: IServiceOrderPayment, thunkAPI) => {
    return axios.patch<IServiceOrderPayment>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
  },
  { serializeError: serializeAxiosError },
);

export const deleteEntity = createAsyncThunk(
  'serviceOrderPayment/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    return await axios.delete<IServiceOrderPayment>(requestUrl);
  },
  { serializeError: serializeAxiosError },
);

// slice

export const ServiceOrderPaymentSlice = createEntitySlice({
  name: 'serviceOrderPayment',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getEntity.fulfilled, (state, action) => {
        state.loading = false;
        state.entity = action.payload.data;
      })
      .addCase(deleteEntity.fulfilled, state => {
        state.updating = false;
        state.updateSuccess = true;
        state.entity = {};
      })
      .addMatcher(isFulfilled(getEntities), (state, action) => {
        const { data, headers } = action.payload;
        const links = parseHeaderForLinks(headers.link);

        return {
          ...state,
          loading: false,
          links,
          entities: loadMoreDataWhenScrolled(state.entities, data, links),
          totalItems: parseInt(headers['x-total-count'], 10),
        };
      })
      .addMatcher(isFulfilled(createEntity, updateEntity, partialUpdateEntity), (state, action) => {
        state.updating = false;
        state.loading = false;
        state.updateSuccess = true;
        state.entity = action.payload.data;
      })
      .addMatcher(isPending(getEntities, getEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isPending(createEntity, updateEntity, partialUpdateEntity, deleteEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.updating = true;
      });
  },
});

export const { reset } = ServiceOrderPaymentSlice.actions;

// Reducer
export default ServiceOrderPaymentSlice.reducer;
