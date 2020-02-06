/*
 * Copyright (c) 2019 Couchbase, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sample.couchbase.spring.mca.config.internal;

import com.couchbase.client.core.ClusterFacade;
import com.couchbase.client.core.message.internal.PingReport;
import com.couchbase.client.core.service.ServiceType;
import com.couchbase.client.java.AsyncBucket;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.PersistTo;
import com.couchbase.client.java.ReplicaMode;
import com.couchbase.client.java.ReplicateTo;
import com.couchbase.client.java.analytics.AnalyticsDeferredResultHandle;
import com.couchbase.client.java.analytics.AnalyticsQuery;
import com.couchbase.client.java.analytics.AnalyticsQueryResult;
import com.couchbase.client.java.bucket.BucketManager;
import com.couchbase.client.java.datastructures.MutationOptionBuilder;
import com.couchbase.client.java.document.Document;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.JsonLongDocument;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.Statement;
import com.couchbase.client.java.repository.Repository;
import com.couchbase.client.java.search.SearchQuery;
import com.couchbase.client.java.search.result.SearchQueryResult;
import com.couchbase.client.java.subdoc.LookupInBuilder;
import com.couchbase.client.java.subdoc.MutateInBuilder;
import com.couchbase.client.java.view.SpatialViewQuery;
import com.couchbase.client.java.view.SpatialViewResult;
import com.couchbase.client.java.view.ViewQuery;
import com.couchbase.client.java.view.ViewResult;
import com.couchbase.client.mc.MultiClusterBucket;
import com.couchbase.client.mc.MultiClusterClient;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SpringMcaBucket implements Bucket {

  private static final TimeUnit TIMEOUT_UNIT = TimeUnit.MILLISECONDS;

  private final MultiClusterBucket multiClusterBucket;
  private final String name;
  private final CouchbaseEnvironment environment;

  private final long kvTimeout;
  private final long queryTimeout;
  private final long viewTimeout;
  private final long searchTimeout;

  public SpringMcaBucket(MultiClusterClient multiClusterClient, String name, CouchbaseEnvironment environment) {
    this.multiClusterBucket = multiClusterClient.openBucket(name);
    this.environment = environment;
    this.name = name;
    this.kvTimeout = environment.kvTimeout();
    this.queryTimeout = environment.queryTimeout();
    this.viewTimeout = environment.viewTimeout();
    this.searchTimeout = environment.searchTimeout();
  }

  @Override
  public AsyncBucket async() {
    throw new UnsupportedOperationException();
  }

  @Override
  public ClusterFacade core() {
    throw new UnsupportedOperationException();
  }

  @Override
  public CouchbaseEnvironment environment() {
    return environment;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public JsonDocument get(String id) {
    return multiClusterBucket.get(id, kvTimeout, TIMEOUT_UNIT);
  }

  @Override
  public JsonDocument get(String id, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.get(id, timeout, timeUnit);
  }

  @Override
  public <D extends Document<?>> D get(D document) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <D extends Document<?>> D get(D document, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <D extends Document<?>> D get(String id, Class<D> target) {
    return multiClusterBucket.get(id, target, kvTimeout, TIMEOUT_UNIT);
  }

  @Override
  public <D extends Document<?>> D get(String id, Class<D> target, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.get(id, target, timeout, timeUnit);
  }

  @Override
  public boolean exists(String id) {
    return multiClusterBucket.exists(id, kvTimeout, TIMEOUT_UNIT);
  }

  @Override
  public boolean exists(String id, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.exists(id, timeout, timeUnit);
  }

  @Override
  public <D extends Document<?>> boolean exists(D document) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <D extends Document<?>> boolean exists(D document, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<JsonDocument> getFromReplica(String id, ReplicaMode type) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Iterator<JsonDocument> getFromReplica(String id) {
    return multiClusterBucket.getFromReplica(id, kvTimeout, TIMEOUT_UNIT);
  }

  @Override
  public List<JsonDocument> getFromReplica(String id, ReplicaMode type, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Iterator<JsonDocument> getFromReplica(String id, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.getFromReplica(id, timeout, timeUnit);
  }

  @Override
  public <D extends Document<?>> List<D> getFromReplica(D document, ReplicaMode type) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <D extends Document<?>> Iterator<D> getFromReplica(D document) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <D extends Document<?>> List<D> getFromReplica(D document, ReplicaMode type, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <D extends Document<?>> Iterator<D> getFromReplica(D document, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <D extends Document<?>> List<D> getFromReplica(String id, ReplicaMode type, Class<D> target) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <D extends Document<?>> Iterator<D> getFromReplica(String id, Class<D> target) {
    return multiClusterBucket.getFromReplica(id, target, kvTimeout, TIMEOUT_UNIT);
  }

  @Override
  public <D extends Document<?>> List<D> getFromReplica(String id, ReplicaMode type, Class<D> target, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <D extends Document<?>> Iterator<D> getFromReplica(String id, Class<D> target, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.getFromReplica(id, target, timeout, timeUnit);
  }

  @Override
  public JsonDocument getAndLock(String id, int lockTime) {
    return multiClusterBucket.getAndLock(id, lockTime, kvTimeout, TIMEOUT_UNIT);
  }

  @Override
  public JsonDocument getAndLock(String id, int lockTime, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.getAndLock(id, lockTime, timeout, timeUnit);
  }

  @Override
  public <D extends Document<?>> D getAndLock(D document, int lockTime) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <D extends Document<?>> D getAndLock(D document, int lockTime, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <D extends Document<?>> D getAndLock(String id, int lockTime, Class<D> target) {
    return multiClusterBucket.getAndLock(id, lockTime, target, kvTimeout, TIMEOUT_UNIT);
  }

  @Override
  public <D extends Document<?>> D getAndLock(String id, int lockTime, Class<D> target, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.getAndLock(id, lockTime, target, timeout, timeUnit);
  }

  @Override
  public JsonDocument getAndTouch(String id, int expiry) {
    return multiClusterBucket.getAndTouch(id, expiry, kvTimeout, TIMEOUT_UNIT);
  }

  @Override
  public JsonDocument getAndTouch(String id, int expiry, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.getAndTouch(id, expiry, timeout, timeUnit);
  }

  @Override
  public <D extends Document<?>> D getAndTouch(D document) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <D extends Document<?>> D getAndTouch(D document, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <D extends Document<?>> D getAndTouch(String id, int expiry, Class<D> target) {
    return multiClusterBucket.getAndTouch(id, expiry, target, kvTimeout, TIMEOUT_UNIT);
  }

  @Override
  public <D extends Document<?>> D getAndTouch(String id, int expiry, Class<D> target, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.getAndTouch(id, expiry, target, timeout, timeUnit);
  }

  @Override
  public <D extends Document<?>> D insert(D document) {
    return multiClusterBucket.insert(document, kvTimeout, TIMEOUT_UNIT);
  }

  @Override
  public <D extends Document<?>> D insert(D document, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.insert(document, kvTimeout, timeUnit);
  }

  @Override
  public <D extends Document<?>> D insert(D document, PersistTo persistTo, ReplicateTo replicateTo) {
    return multiClusterBucket.insert(document, persistTo, replicateTo, kvTimeout, TIMEOUT_UNIT);
  }

  @Override
  public <D extends Document<?>> D insert(D document, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.insert(document, persistTo, replicateTo, timeout, timeUnit);
  }

  @Override
  public <D extends Document<?>> D insert(D document, PersistTo persistTo) {
    return insert(document, persistTo, ReplicateTo.NONE);
  }

  @Override
  public <D extends Document<?>> D insert(D document, PersistTo persistTo, long timeout, TimeUnit timeUnit) {
    return insert(document, persistTo, ReplicateTo.NONE, timeout, timeUnit);
  }

  @Override
  public <D extends Document<?>> D insert(D document, ReplicateTo replicateTo) {
    return insert(document, PersistTo.NONE, replicateTo);
  }

  @Override
  public <D extends Document<?>> D insert(D document, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
    return insert(document, PersistTo.NONE, replicateTo, timeout, timeUnit);
  }

  @Override
  public <D extends Document<?>> D upsert(D document) {
    return multiClusterBucket.upsert(document, kvTimeout, TIMEOUT_UNIT);
  }

  @Override
  public <D extends Document<?>> D upsert(D document, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.upsert(document, kvTimeout, timeUnit);
  }

  @Override
  public <D extends Document<?>> D upsert(D document, PersistTo persistTo, ReplicateTo replicateTo) {
    return multiClusterBucket.upsert(document, persistTo, replicateTo, kvTimeout, TIMEOUT_UNIT);
  }

  @Override
  public <D extends Document<?>> D upsert(D document, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.upsert(document, persistTo, replicateTo, timeout, timeUnit);
  }

  @Override
  public <D extends Document<?>> D upsert(D document, PersistTo persistTo) {
    return upsert(document, persistTo, ReplicateTo.NONE);
  }

  @Override
  public <D extends Document<?>> D upsert(D document, PersistTo persistTo, long timeout, TimeUnit timeUnit) {
    return upsert(document, persistTo, ReplicateTo.NONE, timeout, timeUnit);
  }

  @Override
  public <D extends Document<?>> D upsert(D document, ReplicateTo replicateTo) {
    return upsert(document, PersistTo.NONE, replicateTo);
  }

  @Override
  public <D extends Document<?>> D upsert(D document, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
    return upsert(document, PersistTo.NONE, replicateTo, timeout, timeUnit);
  }

  @Override
  public <D extends Document<?>> D replace(D document) {
    return multiClusterBucket.replace(document, kvTimeout, TIMEOUT_UNIT);
  }

  @Override
  public <D extends Document<?>> D replace(D document, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.replace(document, kvTimeout, timeUnit);
  }

  @Override
  public <D extends Document<?>> D replace(D document, PersistTo persistTo, ReplicateTo replicateTo) {
    return multiClusterBucket.replace(document, persistTo, replicateTo, kvTimeout, TIMEOUT_UNIT);
  }

  @Override
  public <D extends Document<?>> D replace(D document, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.replace(document, persistTo, replicateTo, timeout, timeUnit);
  }

  @Override
  public <D extends Document<?>> D replace(D document, PersistTo persistTo) {
    return replace(document, persistTo, ReplicateTo.NONE);
  }

  @Override
  public <D extends Document<?>> D replace(D document, PersistTo persistTo, long timeout, TimeUnit timeUnit) {
    return replace(document, persistTo, ReplicateTo.NONE, timeout, timeUnit);
  }

  @Override
  public <D extends Document<?>> D replace(D document, ReplicateTo replicateTo) {
    return replace(document, PersistTo.NONE, replicateTo);
  }

  @Override
  public <D extends Document<?>> D replace(D document, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
    return replace(document, PersistTo.NONE, replicateTo, timeout, timeUnit);
  }

  @Override
  public <D extends Document<?>> D remove(D document) {
    return multiClusterBucket.remove(document, kvTimeout, TIMEOUT_UNIT);
  }

  @Override
  public <D extends Document<?>> D remove(D document, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.remove(document, timeout, timeUnit);
  }

  @Override
  public <D extends Document<?>> D remove(D document, PersistTo persistTo, ReplicateTo replicateTo) {
    return multiClusterBucket.remove(document, persistTo, replicateTo, kvTimeout, TIMEOUT_UNIT);
  }

  @Override
  public <D extends Document<?>> D remove(D document, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.remove(document, persistTo, replicateTo, timeout, timeUnit);
  }

  @Override
  public <D extends Document<?>> D remove(D document, PersistTo persistTo) {
    return multiClusterBucket.remove(document, persistTo, ReplicateTo.NONE, kvTimeout, TIMEOUT_UNIT);
  }

  @Override
  public <D extends Document<?>> D remove(D document, PersistTo persistTo, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.remove(document, persistTo, ReplicateTo.NONE, timeout, timeUnit);
  }

  @Override
  public <D extends Document<?>> D remove(D document, ReplicateTo replicateTo) {
    return multiClusterBucket.remove(document, PersistTo.NONE, replicateTo, kvTimeout, TIMEOUT_UNIT);
  }

  @Override
  public <D extends Document<?>> D remove(D document, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.remove(document, PersistTo.NONE, replicateTo, timeout, timeUnit);
  }

  @Override
  public JsonDocument remove(String id) {
    return multiClusterBucket.remove(id, kvTimeout, TIMEOUT_UNIT);
  }

  @Override
  public JsonDocument remove(String id, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.remove(id, timeout, timeUnit);
  }

  @Override
  public JsonDocument remove(String id, PersistTo persistTo, ReplicateTo replicateTo) {
    return multiClusterBucket.remove(id, persistTo, replicateTo, kvTimeout, TIMEOUT_UNIT);
  }

  @Override
  public JsonDocument remove(String id, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.remove(id, persistTo, replicateTo, timeout, timeUnit);
  }

  @Override
  public JsonDocument remove(String id, PersistTo persistTo) {
    return multiClusterBucket.remove(id, persistTo, ReplicateTo.NONE, kvTimeout, TIMEOUT_UNIT);
  }

  @Override
  public JsonDocument remove(String id, PersistTo persistTo, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.remove(id, persistTo, ReplicateTo.NONE, timeout, timeUnit);
  }

  @Override
  public JsonDocument remove(String id, ReplicateTo replicateTo) {
    return multiClusterBucket.remove(id, PersistTo.NONE, replicateTo, kvTimeout, TIMEOUT_UNIT);
  }

  @Override
  public JsonDocument remove(String id, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.remove(id, PersistTo.NONE, replicateTo, timeout, timeUnit);
  }

  @Override
  public <D extends Document<?>> D remove(String id, Class<D> target) {
    return multiClusterBucket.remove(id, target, kvTimeout, TIMEOUT_UNIT);
  }

  @Override
  public <D extends Document<?>> D remove(String id, Class<D> target, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.remove(id, target, timeout, timeUnit);
  }

  @Override
  public <D extends Document<?>> D remove(String id, PersistTo persistTo, ReplicateTo replicateTo, Class<D> target) {
    return multiClusterBucket.remove(id, persistTo, replicateTo, target, kvTimeout, TIMEOUT_UNIT);
  }

  @Override
  public <D extends Document<?>> D remove(String id, PersistTo persistTo, ReplicateTo replicateTo, Class<D> target, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.remove(id, persistTo, replicateTo, target, timeout, timeUnit);
  }

  @Override
  public <D extends Document<?>> D remove(String id, PersistTo persistTo, Class<D> target) {
    return multiClusterBucket.remove(id, persistTo, ReplicateTo.NONE, target, kvTimeout, TIMEOUT_UNIT);
  }

  @Override
  public <D extends Document<?>> D remove(String id, PersistTo persistTo, Class<D> target, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.remove(id, persistTo, ReplicateTo.NONE, target, timeout, timeUnit);
  }

  @Override
  public <D extends Document<?>> D remove(String id, ReplicateTo replicateTo, Class<D> target) {
    return multiClusterBucket.remove(id, PersistTo.NONE, replicateTo, target, kvTimeout, TIMEOUT_UNIT);
  }

  @Override
  public <D extends Document<?>> D remove(String id, ReplicateTo replicateTo, Class<D> target, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.remove(id, PersistTo.NONE, replicateTo, target, timeout, timeUnit);
  }

  @Override
  public ViewResult query(ViewQuery query) {
    return multiClusterBucket.query(query, viewTimeout, TIMEOUT_UNIT);
  }

  @Override
  public SpatialViewResult query(SpatialViewQuery query) {
    return multiClusterBucket.query(query, viewTimeout, TIMEOUT_UNIT);
  }

  @Override
  public ViewResult query(ViewQuery query, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.query(query, timeout, timeUnit);
  }

  @Override
  public SpatialViewResult query(SpatialViewQuery query, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.query(query, timeout, timeUnit);
  }

  @Override
  public N1qlQueryResult query(Statement statement) {
    return multiClusterBucket.query(N1qlQuery.simple(statement), queryTimeout, TIMEOUT_UNIT);
  }

  @Override
  public N1qlQueryResult query(Statement statement, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.query(N1qlQuery.simple(statement), timeout, timeUnit);
  }

  @Override
  public N1qlQueryResult query(N1qlQuery query) {
    return multiClusterBucket.query(query, queryTimeout, TIMEOUT_UNIT);
  }

  @Override
  public N1qlQueryResult query(N1qlQuery query, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.query(query, timeout, timeUnit);
  }

  @Override
  public SearchQueryResult query(SearchQuery query) {
    return multiClusterBucket.query(query, searchTimeout, TIMEOUT_UNIT);
  }

  @Override
  public SearchQueryResult query(SearchQuery query, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.query(query, timeout, timeUnit);
  }

  @Override
  public AnalyticsQueryResult query(AnalyticsQuery query) {
    throw new UnsupportedOperationException();
  }

  @Override
  public AnalyticsQueryResult query(AnalyticsQuery query, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Boolean unlock(String id, long cas) {
    return multiClusterBucket.unlock(id, cas, kvTimeout, TIMEOUT_UNIT);
  }

  @Override
  public Boolean unlock(String id, long cas, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.unlock(id, cas, timeout, timeUnit);
  }

  @Override
  public <D extends Document<?>> Boolean unlock(D document) {
    return unlock(document.id(), document.cas());
  }

  @Override
  public <D extends Document<?>> Boolean unlock(D document, long timeout, TimeUnit timeUnit) {
    return unlock(document.id(), document.cas(), timeout, timeUnit);
  }

  @Override
  public Boolean touch(String id, int expiry) {
    return multiClusterBucket.touch(id, expiry, kvTimeout, TIMEOUT_UNIT);
  }

  @Override
  public Boolean touch(String id, int expiry, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.touch(id, expiry, timeout, timeUnit);
  }

  @Override
  public <D extends Document<?>> Boolean touch(D document) {
    return touch(document.id(), document.expiry());
  }

  @Override
  public <D extends Document<?>> Boolean touch(D document, long timeout, TimeUnit timeUnit) {
    return touch(document.id(), document.expiry(), timeout, timeUnit);
  }

  @Override
  public JsonLongDocument counter(String id, long delta) {
    throw new UnsupportedOperationException();
  }

  @Override
  public JsonLongDocument counter(String id, long delta, PersistTo persistTo) {
    throw new UnsupportedOperationException();
  }

  @Override
  public JsonLongDocument counter(String id, long delta, ReplicateTo replicateTo) {
    throw new UnsupportedOperationException();
  }

  @Override
  public JsonLongDocument counter(String id, long delta, PersistTo persistTo, ReplicateTo replicateTo) {
    throw new UnsupportedOperationException();
  }

  @Override
  public JsonLongDocument counter(String id, long delta, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public JsonLongDocument counter(String id, long delta, PersistTo persistTo, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public JsonLongDocument counter(String id, long delta, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public JsonLongDocument counter(String id, long delta, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public JsonLongDocument counter(String id, long delta, long initial) {
    throw new UnsupportedOperationException();
  }

  @Override
  public JsonLongDocument counter(String id, long delta, long initial, PersistTo persistTo) {
    throw new UnsupportedOperationException();
  }

  @Override
  public JsonLongDocument counter(String id, long delta, long initial, ReplicateTo replicateTo) {
    throw new UnsupportedOperationException();
  }

  @Override
  public JsonLongDocument counter(String id, long delta, long initial, PersistTo persistTo, ReplicateTo replicateTo) {
    throw new UnsupportedOperationException();
  }

  @Override
  public JsonLongDocument counter(String id, long delta, long initial, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public JsonLongDocument counter(String id, long delta, long initial, PersistTo persistTo, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public JsonLongDocument counter(String id, long delta, long initial, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public JsonLongDocument counter(String id, long delta, long initial, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public JsonLongDocument counter(String id, long delta, long initial, int expiry) {
    return multiClusterBucket.counter(id, delta, initial, expiry, kvTimeout, TIMEOUT_UNIT);
  }

  @Override
  public JsonLongDocument counter(String id, long delta, long initial, int expiry, PersistTo persistTo) {
    throw new UnsupportedOperationException();
  }

  @Override
  public JsonLongDocument counter(String id, long delta, long initial, int expiry, ReplicateTo replicateTo) {
    throw new UnsupportedOperationException();
  }

  @Override
  public JsonLongDocument counter(String id, long delta, long initial, int expiry, PersistTo persistTo, ReplicateTo replicateTo) {
    return multiClusterBucket.counter(id, delta, initial, expiry, persistTo, replicateTo, kvTimeout, TIMEOUT_UNIT);
  }

  @Override
  public JsonLongDocument counter(String id, long delta, long initial, int expiry, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.counter(id, delta, initial, expiry, timeout, timeUnit);
  }

  @Override
  public JsonLongDocument counter(String id, long delta, long initial, int expiry, PersistTo persistTo, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public JsonLongDocument counter(String id, long delta, long initial, int expiry, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public JsonLongDocument counter(String id, long delta, long initial, int expiry, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.counter(id, delta, initial, expiry, persistTo, replicateTo, timeout, timeUnit);
  }

  @Override
  public <D extends Document<?>> D append(D document) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <D extends Document<?>> D append(D document, PersistTo persistTo) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <D extends Document<?>> D append(D document, ReplicateTo replicateTo) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <D extends Document<?>> D append(D document, PersistTo persistTo, ReplicateTo replicateTo) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <D extends Document<?>> D append(D document, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <D extends Document<?>> D append(D document, PersistTo persistTo, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <D extends Document<?>> D append(D document, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <D extends Document<?>> D append(D document, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <D extends Document<?>> D prepend(D document) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <D extends Document<?>> D prepend(D document, PersistTo persistTo) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <D extends Document<?>> D prepend(D document, ReplicateTo replicateTo) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <D extends Document<?>> D prepend(D document, PersistTo persistTo, ReplicateTo replicateTo) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <D extends Document<?>> D prepend(D document, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <D extends Document<?>> D prepend(D document, PersistTo persistTo, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <D extends Document<?>> D prepend(D document, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <D extends Document<?>> D prepend(D document, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public LookupInBuilder lookupIn(String docId) {
    throw new UnsupportedOperationException();
  }

  @Override
  public MutateInBuilder mutateIn(String docId) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <V> boolean mapAdd(String docId, String key, V value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <V> boolean mapAdd(String docId, String key, V value, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <V> boolean mapAdd(String docId, String key, V value, MutationOptionBuilder mutationOptionBuilder) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <V> boolean mapAdd(String docId, String key, V value, MutationOptionBuilder mutationOptionBuilder, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <V> V mapGet(String docId, String key, Class<V> valueType) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <V> V mapGet(String docId, String key, Class<V> valueType, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean mapRemove(String docId, String key) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean mapRemove(String docId, String key, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean mapRemove(String docId, String key, MutationOptionBuilder mutationOptionBuilder) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean mapRemove(String docId, String key, MutationOptionBuilder mutationOptionBuilder, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int mapSize(String docId) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int mapSize(String docId, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <E> E listGet(String docId, int index, Class<E> elementType) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <E> E listGet(String docId, int index, Class<E> elementType, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <E> boolean listAppend(String docId, E element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <E> boolean listAppend(String docId, E element, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <E> boolean listAppend(String docId, E element, MutationOptionBuilder mutationOptionBuilder) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <E> boolean listAppend(String docId, E element, MutationOptionBuilder mutationOptionBuilder, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean listRemove(String docId, int index) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean listRemove(String docId, int index, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean listRemove(String docId, int index, MutationOptionBuilder mutationOptionBuilder) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean listRemove(String docId, int index, MutationOptionBuilder mutationOptionBuilder, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <E> boolean listPrepend(String docId, E element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <E> boolean listPrepend(String docId, E element, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <E> boolean listPrepend(String docId, E element, MutationOptionBuilder mutationOptionBuilder) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <E> boolean listPrepend(String docId, E element, MutationOptionBuilder mutationOptionBuilder, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <E> boolean listSet(String docId, int index, E element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <E> boolean listSet(String docId, int index, E element, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <E> boolean listSet(String docId, int index, E element, MutationOptionBuilder mutationOptionBuilder) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <E> boolean listSet(String docId, int index, E element, MutationOptionBuilder mutationOptionBuilder, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int listSize(String docId) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int listSize(String docId, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <E> boolean setAdd(String docId, E element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <E> boolean setAdd(String docId, E element, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <E> boolean setAdd(String docId, E element, MutationOptionBuilder mutationOptionBuilder) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <E> boolean setAdd(String docId, E element, MutationOptionBuilder mutationOptionBuilder, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <E> boolean setContains(String docId, E element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <E> boolean setContains(String docId, E element, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <E> E setRemove(String docId, E element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <E> E setRemove(String docId, E element, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <E> E setRemove(String docId, E element, MutationOptionBuilder mutationOptionBuilder) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <E> E setRemove(String docId, E element, MutationOptionBuilder mutationOptionBuilder, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int setSize(String docId) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int setSize(String docId, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <E> boolean queuePush(String docId, E element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <E> boolean queuePush(String docId, E element, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <E> boolean queuePush(String docId, E element, MutationOptionBuilder mutationOptionBuilder) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <E> boolean queuePush(String docId, E element, MutationOptionBuilder mutationOptionBuilder, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <E> E queuePop(String docId, Class<E> elementType) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <E> E queuePop(String docId, Class<E> elementType, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <E> E queuePop(String docId, Class<E> elementType, MutationOptionBuilder mutationOptionBuilder) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <E> E queuePop(String docId, Class<E> elementType, MutationOptionBuilder mutationOptionBuilder, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int queueSize(String docId) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int queueSize(String docId, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int invalidateQueryCache() {
    throw new UnsupportedOperationException();
  }

  @Override
  public BucketManager bucketManager() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Repository repository() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Boolean close() {
    return multiClusterBucket.close(kvTimeout, TIMEOUT_UNIT);
  }

  @Override
  public Boolean close(long timeout, TimeUnit timeUnit) {
    return multiClusterBucket.close(timeout, timeUnit);
  }

  @Override
  public boolean isClosed() {
    return multiClusterBucket.isClosed();
  }

  @Override
  public PingReport ping(String reportId) {
    throw new UnsupportedOperationException();
  }

  @Override
  public PingReport ping(String reportId, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public PingReport ping() {
    throw new UnsupportedOperationException();
  }

  @Override
  public PingReport ping(long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public PingReport ping(Collection<ServiceType> services) {
    throw new UnsupportedOperationException();
  }

  @Override
  public PingReport ping(Collection<ServiceType> services, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public PingReport ping(String reportId, Collection<ServiceType> services) {
    throw new UnsupportedOperationException();
  }

  @Override
  public PingReport ping(String reportId, Collection<ServiceType> services, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public byte[] exportAnalyticsDeferredResultHandle(AnalyticsDeferredResultHandle handle) {
    throw new UnsupportedOperationException();
  }

  @Override
  public AnalyticsDeferredResultHandle importAnalyticsDeferredResultHandle(byte[] b) {
    throw new UnsupportedOperationException();
  }
}
