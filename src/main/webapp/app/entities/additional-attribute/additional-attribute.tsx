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

import { getEntities, reset } from './additional-attribute.reducer';

export const AdditionalAttribute = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const additionalAttributeList = useAppSelector(state => state.additionalAttribute.entities);
  const loading = useAppSelector(state => state.additionalAttribute.loading);
  const links = useAppSelector(state => state.additionalAttribute.links);
  const updateSuccess = useAppSelector(state => state.additionalAttribute.updateSuccess);

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
      <h2 id="additional-attribute-heading" data-cy="AdditionalAttributeHeading">
        <Translate contentKey="framasaasApp.additionalAttribute.home.title">Additional Attributes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="framasaasApp.additionalAttribute.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/additional-attribute/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="framasaasApp.additionalAttribute.home.createLabel">Create new Additional Attribute</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={additionalAttributeList ? additionalAttributeList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {additionalAttributeList && additionalAttributeList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="framasaasApp.additionalAttribute.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('attributeName')}>
                    <Translate contentKey="framasaasApp.additionalAttribute.attributeName">Attribute Name</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('attributeName')} />
                  </th>
                  <th className="hand" onClick={sort('attributeValue')}>
                    <Translate contentKey="framasaasApp.additionalAttribute.attributeValue">Attribute Value</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('attributeValue')} />
                  </th>
                  <th className="hand" onClick={sort('attributeType')}>
                    <Translate contentKey="framasaasApp.additionalAttribute.attributeType">Attribute Type</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('attributeType')} />
                  </th>
                  <th className="hand" onClick={sort('createddBy')}>
                    <Translate contentKey="framasaasApp.additionalAttribute.createddBy">Createdd By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createddBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdTime')}>
                    <Translate contentKey="framasaasApp.additionalAttribute.createdTime">Created Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdTime')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="framasaasApp.additionalAttribute.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedTime')}>
                    <Translate contentKey="framasaasApp.additionalAttribute.updatedTime">Updated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedTime')} />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.additionalAttribute.franchise">Franchise</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.additionalAttribute.franchiseStatus">Franchise Status</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.additionalAttribute.franchisePerformance">Franchise Performance</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.additionalAttribute.brand">Brand</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.additionalAttribute.category">Category</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.additionalAttribute.address">Address</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.additionalAttribute.location">Location</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.additionalAttribute.franchiseUser">Franchise User</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.additionalAttribute.customer">Customer</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.additionalAttribute.document">Document</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.additionalAttribute.product">Product</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.additionalAttribute.hsn">Hsn</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.additionalAttribute.priceHistory">Price History</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.additionalAttribute.warrantyMaster">Warranty Master</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.additionalAttribute.warrantyMasterPriceHistory">
                      Warranty Master Price History
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.additionalAttribute.article">Article</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.additionalAttribute.articleWarranty">Article Warranty</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.additionalAttribute.articleWarrantyDocument">Article Warranty Document</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.additionalAttribute.serviceOrder">Service Order</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.additionalAttribute.serviceOrderPayment">Service Order Payment</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.additionalAttribute.serviceOrderFranchiseAssignment">
                      Service Order Franchise Assignment
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.additionalAttribute.serviceOrderFieldAgentAssignment">
                      Service Order Field Agent Assignment
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.additionalAttribute.franchiseAllocationRuleSet">
                      Franchise Allocation Rule Set
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.additionalAttribute.franchiseAllocationRule">Franchise Allocation Rule</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.additionalAttribute.fieldAgentSkillRuleSet">Field Agent Skill Rule Set</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.additionalAttribute.fieldAgentSkillRule">Field Agent Skill Rule</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.additionalAttribute.inventoryLocation">Inventory Location</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.additionalAttribute.inventory">Inventory</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.additionalAttribute.serviceOrderAssignment">Service Order Assignment</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {additionalAttributeList.map((additionalAttribute, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/additional-attribute/${additionalAttribute.id}`} color="link" size="sm">
                        {additionalAttribute.id}
                      </Button>
                    </td>
                    <td>{additionalAttribute.attributeName}</td>
                    <td>{additionalAttribute.attributeValue}</td>
                    <td>
                      <Translate contentKey={`framasaasApp.AttributeType.${additionalAttribute.attributeType}`} />
                    </td>
                    <td>{additionalAttribute.createddBy}</td>
                    <td>
                      {additionalAttribute.createdTime ? (
                        <TextFormat type="date" value={additionalAttribute.createdTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{additionalAttribute.updatedBy}</td>
                    <td>
                      {additionalAttribute.updatedTime ? (
                        <TextFormat type="date" value={additionalAttribute.updatedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {additionalAttribute.franchise ? (
                        <Link to={`/franchise/${additionalAttribute.franchise.id}`}>{additionalAttribute.franchise.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {additionalAttribute.franchiseStatus ? (
                        <Link to={`/franchise-status-history/${additionalAttribute.franchiseStatus.id}`}>
                          {additionalAttribute.franchiseStatus.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {additionalAttribute.franchisePerformance ? (
                        <Link to={`/franchise-performance-history/${additionalAttribute.franchisePerformance.id}`}>
                          {additionalAttribute.franchisePerformance.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {additionalAttribute.brand ? (
                        <Link to={`/brand/${additionalAttribute.brand.id}`}>{additionalAttribute.brand.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {additionalAttribute.category ? (
                        <Link to={`/category/${additionalAttribute.category.id}`}>{additionalAttribute.category.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {additionalAttribute.address ? (
                        <Link to={`/address/${additionalAttribute.address.id}`}>{additionalAttribute.address.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {additionalAttribute.location ? (
                        <Link to={`/location-mapping/${additionalAttribute.location.id}`}>{additionalAttribute.location.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {additionalAttribute.franchiseUser ? (
                        <Link to={`/franchise-user/${additionalAttribute.franchiseUser.id}`}>{additionalAttribute.franchiseUser.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {additionalAttribute.customer ? (
                        <Link to={`/customer/${additionalAttribute.customer.id}`}>{additionalAttribute.customer.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {additionalAttribute.document ? (
                        <Link to={`/franchise-document/${additionalAttribute.document.id}`}>{additionalAttribute.document.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {additionalAttribute.product ? (
                        <Link to={`/product/${additionalAttribute.product.id}`}>{additionalAttribute.product.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {additionalAttribute.hsn ? <Link to={`/hsn/${additionalAttribute.hsn.id}`}>{additionalAttribute.hsn.id}</Link> : ''}
                    </td>
                    <td>
                      {additionalAttribute.priceHistory ? (
                        <Link to={`/product-price-history/${additionalAttribute.priceHistory.id}`}>
                          {additionalAttribute.priceHistory.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {additionalAttribute.warrantyMaster ? (
                        <Link to={`/warranty-master/${additionalAttribute.warrantyMaster.id}`}>
                          {additionalAttribute.warrantyMaster.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {additionalAttribute.warrantyMasterPriceHistory ? (
                        <Link to={`/warranty-master-price-history/${additionalAttribute.warrantyMasterPriceHistory.id}`}>
                          {additionalAttribute.warrantyMasterPriceHistory.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {additionalAttribute.article ? (
                        <Link to={`/article/${additionalAttribute.article.id}`}>{additionalAttribute.article.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {additionalAttribute.articleWarranty ? (
                        <Link to={`/article-warranty-details/${additionalAttribute.articleWarranty.id}`}>
                          {additionalAttribute.articleWarranty.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {additionalAttribute.articleWarrantyDocument ? (
                        <Link to={`/article-warranty-details-document/${additionalAttribute.articleWarrantyDocument.id}`}>
                          {additionalAttribute.articleWarrantyDocument.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {additionalAttribute.serviceOrder ? (
                        <Link to={`/service-order/${additionalAttribute.serviceOrder.id}`}>{additionalAttribute.serviceOrder.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {additionalAttribute.serviceOrderPayment ? (
                        <Link to={`/service-order-payment/${additionalAttribute.serviceOrderPayment.id}`}>
                          {additionalAttribute.serviceOrderPayment.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {additionalAttribute.serviceOrderFranchiseAssignment ? (
                        <Link to={`/service-order-franchise-assignment/${additionalAttribute.serviceOrderFranchiseAssignment.id}`}>
                          {additionalAttribute.serviceOrderFranchiseAssignment.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {additionalAttribute.serviceOrderFieldAgentAssignment ? (
                        <Link to={`/service-order-field-agent-assignment/${additionalAttribute.serviceOrderFieldAgentAssignment.id}`}>
                          {additionalAttribute.serviceOrderFieldAgentAssignment.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {additionalAttribute.franchiseAllocationRuleSet ? (
                        <Link to={`/franchise-allocation-rule-set/${additionalAttribute.franchiseAllocationRuleSet.id}`}>
                          {additionalAttribute.franchiseAllocationRuleSet.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {additionalAttribute.franchiseAllocationRule ? (
                        <Link to={`/franchise-allocation-rule/${additionalAttribute.franchiseAllocationRule.id}`}>
                          {additionalAttribute.franchiseAllocationRule.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {additionalAttribute.fieldAgentSkillRuleSet ? (
                        <Link to={`/field-agent-skill-rule-set/${additionalAttribute.fieldAgentSkillRuleSet.id}`}>
                          {additionalAttribute.fieldAgentSkillRuleSet.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {additionalAttribute.fieldAgentSkillRule ? (
                        <Link to={`/field-agent-skill-rule/${additionalAttribute.fieldAgentSkillRule.id}`}>
                          {additionalAttribute.fieldAgentSkillRule.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {additionalAttribute.inventoryLocation ? (
                        <Link to={`/inventory-location/${additionalAttribute.inventoryLocation.id}`}>
                          {additionalAttribute.inventoryLocation.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {additionalAttribute.inventory ? (
                        <Link to={`/inventory/${additionalAttribute.inventory.id}`}>{additionalAttribute.inventory.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {additionalAttribute.serviceOrderAssignment ? (
                        <Link to={`/service-order-assignment/${additionalAttribute.serviceOrderAssignment.id}`}>
                          {additionalAttribute.serviceOrderAssignment.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/additional-attribute/${additionalAttribute.id}`}
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
                          to={`/additional-attribute/${additionalAttribute.id}/edit`}
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
                          onClick={() => (window.location.href = `/additional-attribute/${additionalAttribute.id}/delete`)}
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
                <Translate contentKey="framasaasApp.additionalAttribute.home.notFound">No Additional Attributes found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default AdditionalAttribute;
