package gorae.backend.dto.google;

import lombok.Builder;

@Builder
public record SpaceConfig(
        AccessType accessType,
        EntryPointAccess entryPointAccess,
        Moderation moderation,
        ModerationRestrictions moderationRestrictions,
        AttendanceReportGenerationType attendanceReportGenerationType,
        ArtifactConfig artifactConfig
) {
    public static SpaceConfig forLecture() {
        ModerationRestrictions restrictions = ModerationRestrictions.builder()
                .chatRestriction(SpaceConfig.ModerationRestrictions.RestrictionType.HOSTS_ONLY)
                .presentRestriction(SpaceConfig.ModerationRestrictions.RestrictionType.HOSTS_ONLY)
                .reactionRestriction(SpaceConfig.ModerationRestrictions.RestrictionType.NO_RESTRICTION)
                .build();

        return SpaceConfig.builder()
                .entryPointAccess(EntryPointAccess.ALL)
                .moderation(Moderation.ON)
                .moderationRestrictions(restrictions)
                .build();
    }

    public enum AccessType {
        ACCESS_TYPE_UNSPECIFIED,
        OPEN,
        TRUSTED,
        RESTRICTED
    }

    public enum EntryPointAccess {
        ENTRY_POINT_ACCESS_UNSPECIFIED,
        ALL,
        CREATOR_APP_ONLY
    }

    public enum Moderation {
        MODERATION_UNSPECIFIED,
        OFF,
        ON
    }

    @Builder
    public record ModerationRestrictions(
            RestrictionType chatRestriction,
            RestrictionType reactionRestriction,
            RestrictionType presentRestriction,
            DefaultJoinAsViewerType defaultJoinAsViewerType
    ) {
        public enum RestrictionType {
            RESTRICTION_TYPE_UNSPECIFIED,
            HOSTS_ONLY,
            NO_RESTRICTION
        }

        public enum DefaultJoinAsViewerType {
            DEFAULT_JOIN_AS_VIEWER_TYPE_UNSPECIFIED,
            ON,
            OFF
        }
    }

    public enum AttendanceReportGenerationType {
        ATTENDANCE_REPORT_GENERATION_TYPE_UNSPECIFIED,
        GENERATE_REPORT,
        DO_NOT_GENERATE
    }

    @Builder
    public record ArtifactConfig(
            RecordingConfig recordingConfig,
            TranscriptionConfig transcriptionConfig,
            SmartNotesConfig smartNotesConfig
    ) {
        public record RecordingConfig(AutoGenerationType autoRecordingGeneration) {
        }

        public record TranscriptionConfig(AutoGenerationType autoTranscriptionGeneration) {
        }

        public record SmartNotesConfig(AutoGenerationType autoSmartNotesGeneration) {
        }

        public enum AutoGenerationType {
            AUTO_GENERATION_TYPE_UNSPECIFIED,
            ON,
            OFF
        }
    }
}
