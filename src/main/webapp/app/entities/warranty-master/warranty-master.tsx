import React, { useEffect, useState } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { TextFormat, Translate, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities, reset } from './warranty-master.reducer';

export const WarrantyMaster = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const warrantyMasterList = useAppSelector(state => state.warrantyMaster.entities);
  const loading = useAppSelector(state => state.warrantyMaster.loading);
  const links = useAppSelector(state => state.warrantyMaster.links);
  const updateSuccess = useAppSelector(state => state.warrantyMaster.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    }
    return order === ASC ? faSortUp : faSortDown;
  };

  return (
    <div>
      <h2 id="warranty-master-heading" data-cy="WarrantyMasterHeading">
        <Translate contentKey="framasaasApp.warrantyMaster.home.title">Warranty Masters</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="framasaasApp.warrantyMaster.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/warranty-master/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="framasaasApp.warrantyMaster.home.createLabel">Create new Warranty Master</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={warrantyMasterList ? warrantyMasterList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {warrantyMasterList && warrantyMasterList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="framasaasApp.warrantyMaster.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('name')}>
                    <Translate contentKey="framasaasApp.warrantyMaster.name">Name</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('name')} />
                  </th>
                  <th className="hand" onClick={sort('vendorWarrantyMasterId')}>
                    <Translate contentKey="framasaasApp.warrantyMaster.vendorWarrantyMasterId">Vendor Warranty Master Id</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('vendorWarrantyMasterId')} />
                  </th>
                  <th className="hand" onClick={sort('warrantyType')}>
                    <Translate contentKey="framasaasApp.warrantyMaster.warrantyType">Warranty Type</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('warrantyType')} />
                  </th>
                  <th className="hand" onClick={sort('description')}>
                    <Translate contentKey="framasaasApp.warrantyMaster.description">Description</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                  </th>
                  <th className="hand" onClick={sort('price')}>
                    <Translate contentKey="framasaasApp.warrantyMaster.price">Price</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('price')} />
                  </th>
                  <th className="hand" onClick={sort('periodInMonths')}>
                    <Translate contentKey="framasaasApp.warrantyMaster.periodInMonths">Period In Months</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('periodInMonths')} />
                  </th>
                  <th className="hand" onClick={sort('taxRate')}>
                    <Translate contentKey="framasaasApp.warrantyMaster.taxRate">Tax Rate</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('taxRate')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="framasaasApp.warrantyMaster.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('createddBy')}>
                    <Translate contentKey="framasaasApp.warrantyMaster.createddBy">Createdd By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createddBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdTime')}>
                    <Translate contentKey="framasaasApp.warrantyMaster.createdTime">Created Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdTime')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="framasaasApp.warrantyMaster.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedTime')}>
                    <Translate contentKey="framasaasApp.warrantyMaster.updatedTime">Updated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedTime')} />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.warrantyMaster.product">Product</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {warrantyMasterList.map((warrantyMaster, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/warranty-master/${warrantyMaster.id}`} color="link" size="sm">
                        {warrantyMaster.id}
                      </Button>
                    </td>
                    <td>{warrantyMaster.name}</td>
                    <td>{warrantyMaster.vendorWarrantyMasterId}</td>
                    <td>
                      <Translate contentKey={`framasaasApp.WarrantyType.${warrantyMaster.warrantyType}`} />
                    </td>
                    <td>{warrantyMaster.description}</td>
                    <td>{warrantyMaster.price}</td>
                    <td>{warrantyMaster.periodInMonths}</td>
                    <td>{warrantyMaster.taxRate}</td>
                    <td>{warrantyMaster.isActive ? 'true' : 'false'}</td>
                    <td>{warrantyMaster.createddBy}</td>
                    <td>
                      {warrantyMaster.createdTime ? (
                        <TextFormat type="date" value={warrantyMaster.createdTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{warrantyMaster.updatedBy}</td>
                    <td>
                      {warrantyMaster.updatedTime ? (
                        <TextFormat type="date" value={warrantyMaster.updatedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {warrantyMaster.product ? <Link to={`/product/${warrantyMaster.product.id}`}>{warrantyMaster.product.id}</Link> : ''}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/warranty-master/${warrantyMaster.id}`}
                          color="info"
                          size="sm"
                          data-cy="entityDetailsButton"
                        >
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/warranty-master/${warrantyMaster.id}/edit`}
                          color="primary"
                          size="sm"
                          data-cy="entityEditButton"
                        >
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (window.location.href = `/warranty-master/${warrantyMaster.id}/delete`)}
                          color="danger"
                          size="sm"
                          data-cy="entityDeleteButton"
                        >
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="framasaasApp.warrantyMaster.home.notFound">No Warranty Masters found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default WarrantyMaster;
