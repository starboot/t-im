// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: HeartPacket.proto

package cn.starboot.tim.common.packet.proto;

public final class HeartPacketProto {
  private HeartPacketProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface HeartPacketOrBuilder extends
      // @@protoc_insertion_point(interface_extends:HeartPacket)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <pre>
     * 心跳比特
     * </pre>
     *
     * <code>bytes heartByte = 1;</code>
     * @return The heartByte.
     */
    com.google.protobuf.ByteString getHeartByte();
  }
  /**
   * <pre>
   * IM心跳消息包
   * </pre>
   *
   * Protobuf type {@code HeartPacket}
   */
  public static final class HeartPacket extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:HeartPacket)
      HeartPacketOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use HeartPacket.newBuilder() to construct.
    private HeartPacket(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private HeartPacket() {
      heartByte_ = com.google.protobuf.ByteString.EMPTY;
    }

    @Override
    @SuppressWarnings({"unused"})
    protected Object newInstance(
        UnusedPrivateParameter unused) {
      return new HeartPacket();
    }

    @Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private HeartPacket(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new NullPointerException();
      }
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 10: {

              heartByte_ = input.readBytes();
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return HeartPacketProto.internal_static_HeartPacket_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return HeartPacketProto.internal_static_HeartPacket_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              HeartPacket.class, Builder.class);
    }

    public static final int HEARTBYTE_FIELD_NUMBER = 1;
    private com.google.protobuf.ByteString heartByte_;
    /**
     * <pre>
     * 心跳比特
     * </pre>
     *
     * <code>bytes heartByte = 1;</code>
     * @return The heartByte.
     */
    @Override
    public com.google.protobuf.ByteString getHeartByte() {
      return heartByte_;
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
      if (!heartByte_.isEmpty()) {
        output.writeBytes(1, heartByte_);
      }
      unknownFields.writeTo(output);
    }

    @Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!heartByte_.isEmpty()) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(1, heartByte_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @Override
    public boolean equals(final Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof HeartPacket)) {
        return super.equals(obj);
      }
      HeartPacket other = (HeartPacket) obj;

      if (!getHeartByte()
          .equals(other.getHeartByte())) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + HEARTBYTE_FIELD_NUMBER;
      hash = (53 * hash) + getHeartByte().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static HeartPacket parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static HeartPacket parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static HeartPacket parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static HeartPacket parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static HeartPacket parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static HeartPacket parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static HeartPacket parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static HeartPacket parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static HeartPacket parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static HeartPacket parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static HeartPacket parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static HeartPacket parseFrom(
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
    public static Builder newBuilder(HeartPacket prototype) {
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
     * IM心跳消息包
     * </pre>
     *
     * Protobuf type {@code HeartPacket}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:HeartPacket)
        HeartPacketOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return HeartPacketProto.internal_static_HeartPacket_descriptor;
      }

      @Override
      protected FieldAccessorTable
          internalGetFieldAccessorTable() {
        return HeartPacketProto.internal_static_HeartPacket_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                HeartPacket.class, Builder.class);
      }

      // Construct using cn.starboot.tim.common.packet.proto.HeartPacketProto.HeartPacket.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @Override
      public Builder clear() {
        super.clear();
        heartByte_ = com.google.protobuf.ByteString.EMPTY;

        return this;
      }

      @Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return HeartPacketProto.internal_static_HeartPacket_descriptor;
      }

      @Override
      public HeartPacket getDefaultInstanceForType() {
        return HeartPacket.getDefaultInstance();
      }

      @Override
      public HeartPacket build() {
        HeartPacket result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @Override
      public HeartPacket buildPartial() {
        HeartPacket result = new HeartPacket(this);
        result.heartByte_ = heartByte_;
        onBuilt();
        return result;
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
        if (other instanceof HeartPacket) {
          return mergeFrom((HeartPacket)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(HeartPacket other) {
        if (other == HeartPacket.getDefaultInstance()) return this;
        if (other.getHeartByte() != com.google.protobuf.ByteString.EMPTY) {
          setHeartByte(other.getHeartByte());
        }
        this.mergeUnknownFields(other.unknownFields);
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
        HeartPacket parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (HeartPacket) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private com.google.protobuf.ByteString heartByte_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <pre>
       * 心跳比特
       * </pre>
       *
       * <code>bytes heartByte = 1;</code>
       * @return The heartByte.
       */
      @Override
      public com.google.protobuf.ByteString getHeartByte() {
        return heartByte_;
      }
      /**
       * <pre>
       * 心跳比特
       * </pre>
       *
       * <code>bytes heartByte = 1;</code>
       * @param value The heartByte to set.
       * @return This builder for chaining.
       */
      public Builder setHeartByte(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        heartByte_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       * 心跳比特
       * </pre>
       *
       * <code>bytes heartByte = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearHeartByte() {
        
        heartByte_ = getDefaultInstance().getHeartByte();
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


      // @@protoc_insertion_point(builder_scope:HeartPacket)
    }

    // @@protoc_insertion_point(class_scope:HeartPacket)
    private static final HeartPacket DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new HeartPacket();
    }

    public static HeartPacket getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<HeartPacket>
        PARSER = new com.google.protobuf.AbstractParser<HeartPacket>() {
      @Override
      public HeartPacket parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new HeartPacket(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<HeartPacket> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<HeartPacket> getParserForType() {
      return PARSER;
    }

    @Override
    public HeartPacket getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_HeartPacket_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_HeartPacket_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\021HeartPacket.proto\" \n\013HeartPacket\022\021\n\the" +
      "artByte\030\001 \001(\014B7\n#cn.starboot.tim.common." +
      "packet.protoB\020HeartPacketProtob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_HeartPacket_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_HeartPacket_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_HeartPacket_descriptor,
        new String[] { "HeartByte", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}