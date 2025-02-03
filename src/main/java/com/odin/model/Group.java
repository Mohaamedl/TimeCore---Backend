package com.odin.model;

        import jakarta.persistence.*;
        import java.util.List;

        /**
         * Represents a group entity.
         */
        @Entity
        public class Group {
            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            private Long id;
            private String name;
            private String objectives;
            private String admin;

            @ElementCollection
            private List<String> members;

            @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
            private List<Schedule> schedules;

            // Getters and setters
            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getObjectives() {
                return objectives;
            }

            public void setObjectives(String objectives) {
                this.objectives = objectives;
            }

            public String getAdmin() {
                return admin;
            }

            public void setAdmin(String admin) {
                this.admin = admin;
            }

            public List<String> getMembers() {
                return members;
            }

            public void setMembers(List<String> members) {
                this.members = members;
            }

            public List<Schedule> getSchedules() {
                return schedules;
            }

            public void setSchedules(List<Schedule> schedules) {
                this.schedules = schedules;
            }
        }