// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: AuthPacket.proto

package cn.starboot.tim.common.packet.proto;

public final class AuthPacketProto {
  private AuthPacketProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface AuthPacketOrBuilder extends
      // @@protoc_insertion_point(interface_extends:AuthPacket)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <pre>
     * 用户名ID
     * </pre>
     *
     * <code>string userId = 1;</code>
     * @return The userId.
     */
    String getUserId();
    /**
     * <pre>
     * 用户名ID
     * </pre>
     *
     * <code>string userId = 1;</code>
     * @return The bytes for userId.
     */
    com.google.protobuf.ByteString
        getUserIdBytes();

    /**
     * <pre>
     * token
     * </pre>
     *
     * <code>string token = 2;</code>
     * @return The token.
     */
    String getToken();
    /**
     * <pre>
     * token
     * </pre>
     *
     * <code>string token = 2;</code>
     * @return The bytes for token.
     */
    com.google.protobuf.ByteString
        getTokenBytes();
  }
  /**
   * <pre>
   * IM私有协议包
   * </pre>
   *
   * Protobuf type {@code AuthPacket}
   */
  public static final class AuthPacket extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:AuthPacket)
      AuthPacketOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use AuthPacket.newBuilder() to construct.
    private AuthPacket(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private AuthPacket() {
      userId_ = "";
      token_ = "";
    }

    @Override
    @SuppressWarnings({"unused"})
    protected Object newInstance(
        UnusedPrivateParameter unused) {
      return new AuthPacket();
    }

    @Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return AuthPacketProto.internal_static_AuthPacket_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return AuthPacketProto.internal_static_AuthPacket_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              AuthPacket.class, Builder.class);
    }

    public static final int USERID_FIELD_NUMBER = 1;
    @SuppressWarnings("serial")
    private volatile Object userId_ = "";
    /**
     * <pre>
     * 用户名ID
     * </pre>
     *
     * <code>string userId = 1;</code>
     * @return The userId.
     */
    @Override
    public String getUserId() {
      Object ref = userId_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        userId_ = s;
        return s;
      }
    }
    /**
     * <pre>
     * 用户名ID
     * </pre>
     *
     * <code>string userId = 1;</code>
     * @return The bytes for userId.
     */
    @Override
    public com.google.protobuf.ByteString
        getUserIdBytes() {
      Object ref = userId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        userId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int TOKEN_FIELD_NUMBER = 2;
    @SuppressWarnings("serial")
    private volatile Object token_ = "";
    /**
     * <pre>
     * token
     * </pre>
     *
     * <code>string token = 2;</code>
     * @return The token.
     */
    @Override
    public String getToken() {
      Object ref = token_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        token_ = s;
        return s;
      }
    }
    /**
     * <pre>
     * token
     * </pre>
     *
     * <code>string token = 2;</code>
     * @return The bytes for token.
     */
    @Override
    public com.google.protobuf.ByteString
        getTokenBytes() {
      Object ref = token_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        token_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private byte memoizedIsInitialized = -1;
    @Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(userId_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, userId_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(token_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, token_);
      }
      getUnknownFields().writeTo(output);
    }

    @Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(userId_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, userId_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(token_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, token_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @Override
    public boolean equals(final Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof AuthPacket)) {
        return super.equals(obj);
      }
      AuthPacket other = (AuthPacket) obj;

      if (!getUserId()
          .equals(other.getUserId())) return false;
      if (!getToken()
          .equals(other.getToken())) return false;
      if (!getUnknownFields().equals(other.getUnknownFields())) return false;
      return true;
    }

    @Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + USERID_FIELD_NUMBER;
      hash = (53 * hash) + getUserId().hashCode();
      hash = (37 * hash) + TOKEN_FIELD_NUMBER;
      hash = (53 * hash) + getToken().hashCode();
      hash = (29 * hash) + getUnknownFields().hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static AuthPacket parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static AuthPacket parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static AuthPacket parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static AuthPacket parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static AuthPacket parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static AuthPacket parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static AuthPacket parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static AuthPacket parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static AuthPacket parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static AuthPacket parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static AuthPacket parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static AuthPacket parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(AuthPacket prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @Override
    protected Builder newBuilderForType(
        BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * <pre>
     * IM私有协议包
     * </pre>
     *
     * Protobuf type {@code AuthPacket}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:AuthPacket)
        AuthPacketOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return AuthPacketProto.internal_static_AuthPacket_descriptor;
      }

      @Override
      protected FieldAccessorTable
          internalGetFieldAccessorTable() {
        return AuthPacketProto.internal_static_AuthPacket_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                AuthPacket.class, Builder.class);
      }

      // Construct using cn.starboot.tim.common.packet.proto.AuthPacketProto.AuthPacket.newBuilder()
      private Builder() {

      }

      private Builder(
          BuilderParent parent) {
        super(parent);

      }
      @Override
      public Builder clear() {
        super.clear();
        bitField0_ = 0;
        userId_ = "";
        token_ = "";
        return this;
      }

      @Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return AuthPacketProto.internal_static_AuthPacket_descriptor;
      }

      @Override
      public AuthPacket getDefaultInstanceForType() {
        return AuthPacket.getDefaultInstance();
      }

      @Override
      public AuthPacket build() {
        AuthPacket result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @Override
      public AuthPacket buildPartial() {
        AuthPacket result = new AuthPacket(this);
        if (bitField0_ != 0) { buildPartial0(result); }
        onBuilt();
        return result;
      }

      private void buildPartial0(AuthPacket result) {
        int from_bitField0_ = bitField0_;
        if (((from_bitField0_ & 0x00000001) != 0)) {
          result.userId_ = userId_;
        }
        if (((from_bitField0_ & 0x00000002) != 0)) {
          result.token_ = token_;
        }
      }

      @Override
      public Builder clone() {
        return super.clone();
      }
      @Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return super.setField(field, value);
      }
      @Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return super.addRepeatedField(field, value);
      }
      @Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof AuthPacket) {
          return mergeFrom((AuthPacket)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(AuthPacket other) {
        if (other == AuthPacket.getDefaultInstance()) return this;
        if (!other.getUserId().isEmpty()) {
          userId_ = other.userId_;
          bitField0_ |= 0x00000001;
          onChanged();
        }
        if (!other.getToken().isEmpty()) {
          token_ = other.token_;
          bitField0_ |= 0x00000002;
          onChanged();
        }
        this.mergeUnknownFields(other.getUnknownFields());
        onChanged();
        return this;
      }

      @Override
      public final boolean isInitialized() {
        return true;
      }

      @Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        if (extensionRegistry == null) {
          throw new NullPointerException();
        }
        try {
          boolean done = false;
          while (!done) {
            int tag = input.readTag();
            switch (tag) {
              case 0:
                done = true;
                break;
              case 10: {
                userId_ = input.readStringRequireUtf8();
                bitField0_ |= 0x00000001;
                break;
              } // case 10
              case 18: {
                token_ = input.readStringRequireUtf8();
                bitField0_ |= 0x00000002;
                break;
              } // case 18
              default: {
                if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                  done = true; // was an endgroup tag
                }
                break;
              } // default:
            } // switch (tag)
          } // while (!done)
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.unwrapIOException();
        } finally {
          onChanged();
        } // finally
        return this;
      }
      private int bitField0_;

      private Object userId_ = "";
      /**
       * <pre>
       * 用户名ID
       * </pre>
       *
       * <code>string userId = 1;</code>
       * @return The userId.
       */
      public String getUserId() {
        Object ref = userId_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          userId_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <pre>
       * 用户名ID
       * </pre>
       *
       * <code>string userId = 1;</code>
       * @return The bytes for userId.
       */
      public com.google.protobuf.ByteString
          getUserIdBytes() {
        Object ref = userId_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          userId_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       * 用户名ID
       * </pre>
       *
       * <code>string userId = 1;</code>
       * @param value The userId to set.
       * @return This builder for chaining.
       */
      public Builder setUserId(
          String value) {
        if (value == null) { throw new NullPointerException(); }
        userId_ = value;
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }
      /**
       * <pre>
       * 用户名ID
       * </pre>
       *
       * <code>string userId = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearUserId() {
        userId_ = getDefaultInstance().getUserId();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
        return this;
      }
      /**
       * <pre>
       * 用户名ID
       * </pre>
       *
       * <code>string userId = 1;</code>
       * @param value The bytes for userId to set.
       * @return This builder for chaining.
       */
      public Builder setUserIdBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) { throw new NullPointerException(); }
        checkByteStringIsUtf8(value);
        userId_ = value;
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }

      private Object token_ = "";
      /**
       * <pre>
       * token
       * </pre>
       *
       * <code>string token = 2;</code>
       * @return The token.
       */
      public String getToken() {
        Object ref = token_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          token_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <pre>
       * token
       * </pre>
       *
       * <code>string token = 2;</code>
       * @return The bytes for token.
       */
      public com.google.protobuf.ByteString
          getTokenBytes() {
        Object ref = token_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          token_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       * token
       * </pre>
       *
       * <code>string token = 2;</code>
       * @param value The token to set.
       * @return This builder for chaining.
       */
      public Builder setToken(
          String value) {
        if (value == null) { throw new NullPointerException(); }
        token_ = value;
        bitField0_ |= 0x00000002;
        onChanged();
        return this;
      }
      /**
       * <pre>
       * token
       * </pre>
       *
       * <code>string token = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearToken() {
        token_ = getDefaultInstance().getToken();
        bitField0_ = (bitField0_ & ~0x00000002);
        onChanged();
        return this;
      }
      /**
       * <pre>
       * token
       * </pre>
       *
       * <code>string token = 2;</code>
       * @param value The bytes for token to set.
       * @return This builder for chaining.
       */
      public Builder setTokenBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) { throw new NullPointerException(); }
        checkByteStringIsUtf8(value);
        token_ = value;
        bitField0_ |= 0x00000002;
        onChanged();
        return this;
      }
      @Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:AuthPacket)
    }

    // @@protoc_insertion_point(class_scope:AuthPacket)
    private static final AuthPacket DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new AuthPacket();
    }

    public static AuthPacket getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<AuthPacket>
        PARSER = new com.google.protobuf.AbstractParser<AuthPacket>() {
      @Override
      public AuthPacket parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        Builder builder = newBuilder();
        try {
          builder.mergeFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.setUnfinishedMessage(builder.buildPartial());
        } catch (com.google.protobuf.UninitializedMessageException e) {
          throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
        } catch (java.io.IOException e) {
          throw new com.google.protobuf.InvalidProtocolBufferException(e)
              .setUnfinishedMessage(builder.buildPartial());
        }
        return builder.buildPartial();
      }
    };

    public static com.google.protobuf.Parser<AuthPacket> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<AuthPacket> getParserForType() {
      return PARSER;
    }

    @Override
    public AuthPacket getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_AuthPacket_descriptor;
  private static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_AuthPacket_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\020AuthPacket.proto\"+\n\nAuthPacket\022\016\n\006user" +
      "Id\030\001 \001(\t\022\r\n\005token\030\002 \001(\tB6\n#cn.starboot.t" +
      "im.common.packet.protoB\017AuthPacketProtob" +
      "\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_AuthPacket_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_AuthPacket_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_AuthPacket_descriptor,
        new String[] { "UserId", "Token", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
