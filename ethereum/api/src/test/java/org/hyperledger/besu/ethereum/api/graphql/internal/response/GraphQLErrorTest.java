/*
 * Copyright contributors to Besu.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package org.hyperledger.besu.ethereum.api.graphql.internal.response;

import static org.assertj.core.api.Assertions.assertThat;

import org.hyperledger.besu.ethereum.api.jsonrpc.JsonRpcErrorConverter;
import org.hyperledger.besu.ethereum.api.jsonrpc.internal.response.RpcErrorType;
import org.hyperledger.besu.ethereum.transaction.TransactionInvalidReason;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class GraphQLErrorTest {

  @ParameterizedTest
  @EnumSource(
      value = TransactionInvalidReason.class,
      names = {
        "EXCEEDS_TRANSACTION_GAS_LIMIT",
        "REPLAY_PROTECTED_SIGNATURE_REQUIRED",
        "EXCEEDS_MAX_TX_BYTES",
        "MAX_PRIORITY_FEE_PER_GAS_EXCEEDS_MAX_FEE_PER_GAS",
        "INVALID_TRANSACTION_FORMAT",
        "TRANSACTION_ALREADY_KNOWN",
        "TRANSACTION_REPLACEMENT_UNDERPRICED",
        "NONCE_TOO_FAR_IN_FUTURE_FOR_SENDER",
        "TOTAL_BLOB_GAS_TOO_HIGH",
        "TX_POOL_DISABLED",
        "PLUGIN_TX_VALIDATOR"
      })
  public void transactionPoolRejectionMatchesJsonRpcError(final TransactionInvalidReason reason) {
    final GraphQLError graphQLError = GraphQLError.of(reason);
    final RpcErrorType rpcError = JsonRpcErrorConverter.convertTransactionInvalidReason(reason);

    assertThat(graphQLError.getCode()).isEqualTo(rpcError.getCode());
    assertThat(graphQLError.getMessage()).isEqualTo(rpcError.getMessage());
  }
}
